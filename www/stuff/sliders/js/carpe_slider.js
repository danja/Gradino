// -------------------------------+
// CARPE Slider     version 2.0.3 |
// 2011 - 09 - 21                 |
// By Tom Hermansson Snickars     |
// Copyright CARPE Design         |
// http://carpe.ambiprospect.com/ |
//--------------------------------+

/* To do:
	CSS units
	Plain object as target
	disabled="disabled"
*/

// This script requires carpe_common.js (version 0.4 is recommended)
var CARPE = CARPE || {},
	console = window.console || {};
	
console.log = console.log || function () {};
console.warn = console.warn	|| function () {};
console.error = console.error || function () {};
console.info = console.info	|| function () {};

CARPE.Sliders = {
	version: '2.0.3',
	elements: [], // Array that holds all slider objects in a page.

	// Method 'init': Loops through slider elements and creates the corresponding slider objects. 
	init: function () {
		var i,
			doc = window.document,
			sliderClass = CARPE.Slider.prototype,
			elements = CARPE.Sliders.elements = CARPE.getElementsByClass(sliderClass.sliderClassName),
			len = elements.length,
			classPrefix = sliderClass.panelClassName + sliderClass.separator;
		for (i = 0; i < len; i = i + 1) {
			if (!doc.getElementById(classPrefix + elements[i].id)) {
				new CARPE.Slider(elements[i]);
			}
		}
		return;
	}
};

/*	The 'Slider' class constructor: elmnt is a proper input element object or an id string.
		params is an object with the optional properties..
			orientation ('horizontal' or 'vertical'),
			value (number 0-1),
			position (number of pixels),
			size (number of pixels),
			stops (number of stops that the slider can snap to),
			target (target element or object),
			feedback (true or false, true means that snapping at the target controls snapping for the slider)
*/
CARPE.Slider = function (elmnt, params, DOMLocation) {
	// The original (X)HTML slider element:
	var document = window.document,
		i,
		j,
		id,
		idBase,
		startElmnt = {},
		actualElement = {},
		doc = window.document,
		el,
		props = {},
		classNames = [],
		options = [],
		stops,
		name,
		val,
		node,
		atts = [],
		prefix,
		prefixLength,
		valueElmnt = {},
		panel = {},
		slit = {},
		knob = {},
		width,
		height;
	if (elmnt) {
		if (typeof elmnt === 'string') {
			id = elmnt;
			actualElement = document.getElementById(elmnt) || null;
		} else if (elmnt.nodeType === 1) {
			actualElement = elmnt;
			options = elmnt.options || null;
			stops = options ? (options.length || undefined) : undefined;
		}
	}
	// Create the element if it's not there.
	startElmnt = actualElement || document.createElement('input');
	
	// The slider ID:
	if (!startElmnt.id && !id) {
		id = 0;
		idBase = this.sliderClassName + this.separator + this.idPrefix + this.separator;
		while (document.getElementById(idBase + id)) {
			id = id + 1;
		} // Find a unique id.
		startElmnt.id = idBase + id;
	}
	this.id = startElmnt.id || id;
	this.attributes = startElmnt.attributes || [];
	
	// The name attribute needed to send values to server:
	this.name = startElmnt.name || this.id;
	
	// The Slider Classname:
	if (startElmnt.className) {
		this.className = (startElmnt.className.indexOf(this.sliderClassName) === -1) ?
			(startElmnt.className + ' ' + this.sliderClassName) : startElmnt.className;
	} else {
		this.className = this.sliderClassName;
	}
	
	// Default values
	this.disabled = false;
	this.orientation = 'horizontal';
	this.position = 0;
	this.value = startElmnt.value ? parseFloat(startElmnt.value) : null;
	this.size = startElmnt.style.width ? 
		parseInt(startElmnt.style.width, 10) : 100;
	this.from = 0;
	this.to = 1;
	this.min = null;
	this.max = null;
	this.unit = 'px';
	this.targets = [];
	this.hasSlit = true;
	this.hasPanel = true;
	this.hasKnob = true;
	this.stops = this.stops || 0;
	this.decimals = 14;
	this.range = 1;
	this.scale = this.range / this.size;
	
	// The local function assignToProp: takes an object of name/value pairs, and tries
	// to assign values to the slider properties regardless of the method of user customization.
	function assignToProps(list) {
		var i,
			name,
			val;
		for (i in list) {
			name = i;
			val = list[i];
			switch (name) {
			case 'disabled':	this.disabled = true; break;
			case 'orientation':	this.orientation = val; break;
			case 'position':	this.position = parseInt(val, 10); break;
			case 'value':		this.value = parseFloat(val); break;
			case 'size':		this.size = parseInt(val, 10); break;
			case 'from':		this.from = parseFloat(val, 10); break;
			case 'to':			this.to = parseFloat(val, 10); break;
			case 'min':			this.min = parseFloat(val, 10); break;
			case 'max':			this.max = parseFloat(val, 10); break;
			case 'unit':		this.unit = (val === 'px' || val === 'mm' || val === 'em') ? val : this.unit; break;
			case 'slit':		this.hasSlit = (val === 'false' || val === 'no') ? false : this.hasSlit; break;
			case 'panel':		this.hasPanel = (val === 'false' || val === 'no') ? false : this.hasPanel; break;
			case 'knob':		this.hasKnob = (val === 'false' || val === 'no') ? false : this.hasKnob; break;
			case 'stops':		this.stops = parseInt(val, 10); break;
			case 'step':		this.step = parseFloat(val, 10); this.stops = 0; break;
			case 'decimals':	this.decimals = parseInt(val, 10); break;
			case 'targets':		this.targets = val.toString().split(' '); break;
			case 'target':		this.target = val; break;
			case 'feedback':	this.feedback = ((val === 'true') || (val === 'yes')) ? true : !!val; break;
			default: break;
			}
		}
	}
	// Properties supplied as constructor arguments:
	if (params) {
		CARPE.transfer(params, props);
	}
	// Properties supplied with the class attribute (recommended with HTML 4 and XHTML):
	classNames = this.className ? this.className.split(' ') : [];
	len = classNames.length;
	for (i = 0; i < len; i = i + 1) {
		name = classNames[i].split(this.separator)[0];
		props[name] = classNames[i].substring(name.length + 1, classNames[i].length); 
	}
	// Properties supplied with custom carpe attributes (recommended with HTML 5):
	atts = this.attributes;
	len = atts.length;
	prefix = this.attributePrefix;
	prefixLength = this.attributePrefix.length;
	for (var i = 0; i < len; i = i + 1) {
		props[atts[i].nodeName.replace(prefix, '')] = atts[i].nodeValue;
		props[atts[i].nodeName] = atts[i].nodeValue;
	}
	assignToProps.call(this, props);

	// Initial values for slider movement limitation and position:
	if (this.max !== null) {
		this.to = this.max;
	} else {
		this.max = Math.max(this.to, this.from);
	}
	if (this.min !== null) {
		this.from = this.min;
	} else {
		this.min = Math.min(this.to, this.from);
	}
	if (this.max < this.min) {
		i = this.max;
		this.max = this.min;
		this.min = i;
	}
	if (this.target) {
		this.targets.push(this.target);
	}
	len = this.targets.length;
	j = 0;
	if (len) {
		for (i = 0; i < len; i = i + 1) {
			el = doc.getElementById(this.targets[i])
			if (el) {
				this.targets[j] = doc.getElementById(this.targets[i]);
				j = j + 1;
			}
		}
		this.targets.splice(j, len - j);
	}
	this.vertical = (this.orientation === 'vertical'); 
	this.range = this.to - this.from;
	this.scale = this.range / this.size;
	this.xMax = !this.vertical ? this.size : null;
	this.yMax = this.vertical ? this.size : null;
	this.value = this.value > this.max ? this.max : (this.value < this.min ? this.min : this.value);
	this.position = this.position > this.size ? this.size : this.position;
	if (this.value === null) {
		this.value = this.size > 0 ?
			(this.position / this.size) * this.range + this.from : 0;
	} else {
		this.position = this.value < this.max ? Math.round((this.value - this.min) /
			this.range * this.size) : this.size;
	}
	this.x = this.y = this.position;

	// Parent element and the slider's DOM location:
	if (startElmnt.parentNode) {
		this.parent = startElmnt.parentNode;
	} else {
		this.parent = document.forms[0] || document;
		if (DOMLocation) {
			if (DOMLocation.parent) { 
				this.parent = (typeof DOMLocation.parent === 'string') ?
					document.getElementById(DOMLocation.parent) : DOMLocation.parent;
			} 
			if (DOMLocation.before) {
				this.before = (typeof DOMLocation.before === 'string') ?
					document.getElementById(DOMLocation.before) : DOMLocation.before;
				this.parent = this.before.parentNode;
			}
			if (DOMLocation.after) {
				this.after = (typeof DOMLocation.after === 'string') ?
					document.getElementById(DOMLocation.after) : DOMLocation.after;
				this.parent = this.after.parentNode;
			}
		}
		// The slider's location in the DOM
		if (!this.before && !this.after) {
			this.parent.appendChild(startElmnt);
		} else if (this.before) {
			this.parent.insertBefore(startElmnt, this.before);
		} else if (this.after) {
			node = this.after.nextSibling;
			while (node.nodeType !== 1) {
				node = node.nextSibling;
			}
			this.parent.insertBefore(startElmnt, node);
		}
	}
	
	// The new hidden value element:
	valueElmnt = document.createElement('input');
	valueElmnt.setAttribute('type', 'hidden');
	valueElmnt.setAttribute('name', this.name);
	valueElmnt.className = this.className;
	this.parent.insertBefore(valueElmnt, startElmnt);
	this.parent.removeChild(startElmnt);
	valueElmnt.disabled = startElmnt.disabled;
	valueElmnt.id = this.id;
	valueElmnt.knob = this.knob;
	valueElmnt.panel = this.panel;
	valueElmnt.slit = this.slit;
	valueElmnt.setValue = function () { this.setValue.call(this); };
	this.valueElmnt = valueElmnt;
		
	// The slider panel:
	panel = document.createElement('a');
	panel.setAttribute('href', 'javascript: void 0;');
	panel.setAttribute('tabindex', '0');
	panel.style.cssText = startElmnt.style.cssText; // Copy styles from the user supplied input element.
	this.parent.insertBefore(panel, valueElmnt);
	panel.className = this.panelClassName + ' orientation-' + 
		this.orientation; // + ' target-' + this.target.id;
	panel.id = this.panelClassName + '-' + this.id;
	this.panel = panel;
	
	// The slider knob:
	knob = document.createElement('div');
	knob.className = this.knobClassName;
	knob.id = this.knobClassName + '-' + this.id;
	this.panel.appendChild(knob);
	knob.width = parseInt(CARPE.getStyle(knob, 'width'), 10);
	knob.height = parseInt(CARPE.getStyle(knob, 'height'), 10);
	if (!this.vertical) {
		width = this.size + knob.width;
		if (!window.opera) {
			width = width + parseInt(CARPE.getStyle(knob, 'border-left-width'), 10) +
				parseInt(CARPE.getStyle(knob, 'border-right-width'), 10);
		}
		knob.size = width;
		this.panel.style.width = width.toString() + this.unit;
	} else {
		height = this.size + knob.height;
		if (!window.opera) {
			height = height + parseInt(CARPE.getStyle(knob, 'border-top-width'), 10) +
				parseInt(CARPE.getStyle(knob, 'border-bottom-width'), 10);
		}
		knob.size = height;
		this.panel.style.height = height.toString() + this.unit;
	}
	this.knob = knob;
	this.knob.location = this.bind(CARPE.position, this.knob);
	
	// The slider slit:
	if (this.hasSlit) {
		slit = document.createElement('div');
		slit.className = this.slitClassName;
		slit.id = this.slitClassName + '-' + this.id;
		this.panel.appendChild(slit);
		if (!this.vertical) {
			slit.style.width = (this.size + this.knob.width -
			parseInt(CARPE.getStyle(slit, 'border-left-width'), 10) -
			parseInt(CARPE.getStyle(slit, 'border-right-width'), 10)).toString() +
				this.unit;
			if (window.opera) {
				slit.style.width = (parseInt(slit.style.width, 10) -
				parseInt(CARPE.getStyle(knob, 'border-left-width'), 10) -
				parseInt(CARPE.getStyle(knob, 'border-right-width'), 10)).toString() +
					this.unit;
			}
		} else {
			slit.style.height = (this.size + knob.height -
			parseInt(CARPE.getStyle(slit, 'border-top-width'), 10) -
			parseInt(CARPE.getStyle(slit, 'border-bottom-width'), 10)).toString() +
				this.unit;
			if (window.opera) {
				slit.style.height = (parseInt(slit.style.height, 10) -
				parseInt(CARPE.getStyle(knob, 'border-top-width'), 10) -
				parseInt(CARPE.getStyle(knob, 'border-bottom-width'), 10)).toString() +
					this.unit;
			}
		}
	}
	this.slit = slit;

	// Event handlers:
	CARPE.addEventListener(this.knob, 'mousedown', this.bind(this.start, this));
	CARPE.addEventListener(this.panel, 'mousedown', this.bind(this.jump, this));
	this.mouseMoveListener = this.bind(this.move, this);
	this.mouseUpListener = this.bind(this.mouseUp, this);
	this.panel.onblur = this.bind(this.makeBlurred, this);
	if (window.opera) {
		this.panel.onkeypress = this.bind(this.keyHandler, this);
	} else {
		this.panel.onkeydown = this.bind(this.keyHandler, this);
	}
	
	// Move slider knob to initial position and set value:
	this.update();
	this.enabled(this.enabled());
	
	// Add slider to array of sliders:
	CARPE.Sliders.elements.concat(this);
};

// The Slider class:
CARPE.Slider.prototype = {
	separator:			'-',		// Separator used for name/value pairs sent by the class attribute.
	idPrefix:			'auto', // Prefix used for auto-generated slider element IDs.
	sliderClassName:	'carpe-slider',			 // The class name for the silders.
	panelClassName:		'carpe-slider-panel', // CSS selector for the slider panel.
	slitClassName:		'carpe-slider-slit',	// CSS selector for the slider slit.
	knobClassName:		'carpe-slider-knob',	// CSS selector for the slider knob.
	attributePrefix:	'data-carpe-',	// Attribute prefix for Carpe Slider custom attributes.
	KEY_LEFT:			37,
	KEY_UP:				38,
	KEY_RIGHT:			39,
	KEY_DOWN:			40,

	// Class method 'bind': binds a function or method to an invocation context.
	bind: function (method, context) {
		return function () {
			return method.apply(context, arguments);
		};
	},
	// Class method 'makeFocused': handles added focus.
	makeFocused: function () {
		// this.slit.className = this.slitClassName + ' focus';
		// this.panel.focus();
		// this.temp = this.bind(this.keyHandler, this);
		// this.panel.onkeydown = this.temp;
	},
	// Class method 'makeBlurred': handles removed focus.
	makeBlurred: function () {
		if (this.hasSlit) {
			this.slit.className = this.slitClassName;
		}
		// this.panel.blur();
		// this.panel.onkeydown = null;
		return this;
	},
	// Class method 'keyHandler': handles arrow key input for slider.
	keyHandler: function (evnt) {
		var evnt = evnt || window.event, // Get the key event.
			LEFT = 37,
			RIGHT = 39,
			UP = 38,
			DOWN = 40,
			key;
		if (evnt && !this.disabled) {
			key = evnt.which || evnt.keyCode; // Get the key code.
			if ((key === RIGHT) || (key === UP)) {
				this.moveInc(2 * (-this.vertical) + 1); 
				return false;
			} else if ((key === LEFT) || (key === DOWN)) {
				this.moveInc(2 * (+this.vertical) - 1);
				return false;
			} else {
			return true;
			}
		}
	},
	// Class method 'moveInc': moves slider a number of steps (pixels if no 'steps' attribute is specified).
	moveInc: function (increment) {
		var i,
			stops,
			distance = (this.stops > 1) ? increment * this.size / (this.stops - 1) : increment;
		if (this.step > 0) {
			distance = increment * this.step / this.scale;
			stops = this.scale * this.size / this.step;
			if (!this.vertical) {
				if ((this.position === this.size) && (increment < 0)) {
					distance = this.step * (increment + 1 + Math.floor(stops) - stops) / this.scale;
				}
			} else {
				if ((this.position === 0) && (increment > 0)) {
					distance = this.step * (increment - stops + Math.floor(stops)) / this.scale;
				}
			}
		}
		this.position = this.position + distance;
		return this.update();
	},
	// Class method 'snap': moves slider to fixed positions specified by 'stops' or 'step'.
	snap: function () {
		if (this.stops > 1) { // HTML 4 and XHTML model.
			this.position = parseInt(this.size * Math.round(this.position *
				(this.stops - 1) / this.size) / (this.stops - 1), 10);
		} else if (this.step > 0) { // HTML5 model.
			if ((this.size - this.position) < (this.step / 2 )) {
				this.position = this.size;
			} else {
				this.position = parseInt(Math.round(this.scale * this.position / this.step) *
					this.step / this.scale, 10);
			}
		}
		return this.update();
	},
	// Class method 'mouseUp': handles the end of the sliding process.
	mouseUp: function () {
		this.sliding = false; 
		this.snap();
		CARPE.removeEventListener(document, 'mousemove', this.mouseMoveListener);
		CARPE.removeEventListener(document, 'mouseup', this.mouseUpListener);
		if (this.hasSlit) {
			this.slit.className = this.slitClassName;
		}
		return this;
	},
	// Class method 'start': handles the start of the sliding process.
	start: (function () { function sxxx(evnt) {
		evnt = evnt || window.evnt; // Get the mouse event causing the slider activation.
		this.startOffsetX = this.x - evnt.screenX; // Horizontal slider-mouse offset at start of slide.
		this.startOffsetY = this.y - evnt.screenY; // Vertical slider-mouse offset at start of slide.
		this.panel.focus();
		CARPE.stopEvent(evnt);
		if (!this.disabled) { this.sliding = true; }
		CARPE.addEventListener(document, 'mousemove', this.mouseMoveListener); // Start the action if
			// the mouse is dragged.
		CARPE.addEventListener(document, 'mouseup', this.mouseUpListener); // Stop sliding on mouseup.
		return true;
	}; return sxxx;})(),
	// Class method 'jump': handles an instant movement of the slider when user clicks on the panel.
	jump: function (evnt) {
		evnt = evnt || window.event; // Get the mouse event causing the slider activation.
		CARPE.stopEvent(evnt);
		if (!this.disabled) {
			var scroll = CARPE.getScrollPosition(),
				pos = CARPE.getPos(this.knob),
				borderWidth = { x: parseInt(CARPE.getStyle(this.knob, 'border-left-width') +
					CARPE.getStyle(this.knob, 'border-right-width'), 10),
					y: parseInt(CARPE.getStyle(this.knob,
					'border-top-width') + CARPE.getStyle(this.knob, 'border-bottom-width'), 10)};
			if (this.vertical) { // Move slider to new horizontal position.
				this.position = (evnt.clientY - (pos.y - scroll.y - this.y +
					parseInt((borderWidth.y + this.knob.height / 2), 10)));
			} else {
				this.position = (evnt.clientX - (pos.x - scroll.x - this.x +
					parseInt((borderWidth.x + this.knob.width / 2), 10)));
			}
			this.update();
			this.start(evnt);
		}
		return true;
	},
	// Class method 'move': handles the movement of the slider while dragging.
	move: (function () { function msxxx(evnt) {
		evnt = evnt || window.event;
		if (this.sliding) {
			this.position = this.vertical * (this.startOffsetY + evnt.screenY) +
				(!this.vertical) * (this.startOffsetX + evnt.screenX);
			this.update();
			return false;
		} else {
			return true;
		}
	}; return msxxx; })(),

	// Class method 'pos': Moves the slider to a new pixel position and returns the slider object.
	pos: function () {
		var position = this.position,
			pos = {},
			dir = this.vertical ? 'y' : 'x';
		this.position = this[dir] = (position > this.size) ? this.size : ((position < 0) ? 0 : position);
		pos[dir] = this[dir];
		this.knob.location(pos);
		return this;
	},
	// Class method 'val': Calculates and sets the value of the hidden input element,
	// and the sliders value property and returns the slider object.
	val: function () {
		var v = this.vertical,
			dir = v ? 'y' : 'x',
			val = this[dir] * (1 - 2 * v) * this.scale + this.from * (!v) + this.to * v;
		this.valueElmnt.value = this.value = CARPE.roundToDec(val, this.decimals);
		return this;
	},
	// Class method 'setValue': Sets value and positions knob accordingly.
	// Intended as a 'public' method for user scripts, and when a display element publishes a feedback.
	setValue: function (value) {
		this.position = parseInt((value - this.from) / this.scale, 10);
		return this.update(true);
	},
	// Class method 'updateTargets': Sets the value for the target element.
	updateTargets: function (feedback) {
		var i,
			len,
			val = this.value,
			target,
			targets = this.targets || null;
		if (targets) {
			len = targets.length;
			for (i = 0; i < len; i = i + 1) {
				target = targets[i];
				if (target.setValue) {
					target.setValue(val);
				} else if (document.getElementById(target.id).setValue) {
					target = document.getElementById(target.id);
					target.setValue(val);
				} else {
					target.value = val;
				}
				if (feedback) {
					if (target.getValue && target.getValue) {
						true;
					}
				}
			}
			return this;
		}
		else {
			return false;
		}
	},
	// Class method 'update': Used only when the slider is a target for other form elements.
	update: function (prevent) {
		this.pos().val();
		if (prevent) return this;
		else return this.updateTargets();
	},
	// Class method 'enabled': Updates (from DOM), sets or gets disabled status. 
	enabled: function () {
		var disabledAttr = this.valueElmnt.disabled,
			doc = window.document,
			dis = "disabled",
			temp;
		if (disabledAttr !== this.disabled) {
			this.disabled = disabledAttr;
		}
		if (arguments.length > 0) {
			this.disabled = !arguments[0];
			if (this.disabled) {
				this.valueElmnt.disabled = false;
				temp1 = doc.createAttribute(dis);
				temp2 = doc.createAttribute(dis);
				temp3 = doc.createAttribute(dis);
				temp1.value = temp2.value = temp3.value = dis;
				this.panel.setAttributeNode(temp1);
				this.knob.setAttributeNode(temp2);
				this.slit.setAttributeNode(temp3);
			} else {
				this.valueElmnt.disabled = true;
				this.panel.removeAttribute(dis);
				this.knob.removeAttribute(dis);
				this.slit.removeAttribute(dis);
			}
		};
		return !this.disabled;
	}
};
// Make sure the document is loaded before the slider initiation starts:
CARPE.domLoaded(CARPE.Sliders.init);

// End of script.