//---------------------------------+
//  CARPE Common       version 0.5 |
//  2011 - 06 - 26                 |
//  By Tom Hermansson Snickars     |
//  Copyright CARPE Design         |
//  http://carpe.ambiprospect.com/ |
//---------------------------------+

var CARPE = {
	version: '0.5',
	transfer: function (source, target, targetPriority) {
    var i;
    for (i in source) {
			if (!targetPriority || (target[i] === undefined)) {
        target[i] = source[i];
      }
    }
	},
	bind: function (obj) {
		var method = this,
      temp = function () {
        return method.apply(obj, arguments);
      };
    return temp;
	},
	// getElementsByClass: Returns an array with all elements that
	// have a class attribute that contains 'className'.
	getElementsByClass: function (className) {
		var i, j, classElements = [],
      els = document.getElementsByTagName("*"),
      pattern = new RegExp("(^|\\s)" + className + "(\\s|$)");
		for (i = 0, j = 0; i < els.length; i = i + 1) {
			if (pattern.test(els[i].className)) {
				classElements[j] = els[i];
				j = j + 1;
			}
		}
		return classElements;
	},
	// position: handles both horizontal and vertical relative positioning of elements.
	// Returns or sets the position of an element as an object: { 'x': x, 'y': y }.
	position: function (pos) {
    var elmnt = this;
    function modPos(dir, val) {
      var val = (val || (val === 0)) ? val : null,
        style = elmnt.style;
      if (style) {
        if (typeof (style[dir]) === 'string') {
          if (val !== null) { // setter.
            style[dir] = val.toString() + 'px';
          } else { // getter.
            val = parseInt(style[dir], 10);
          }
        } else if (style[CARPE.camelize('pixel' + dir)]) {
          if (val !== null) { // setter.
            style[CARPE.camelize('pixel' + dir)] = val;
          } else { // getter.
            val = elmnt.style[CARPE.camelize('pixel' + dir)];
          }
        }
      }
    }
    if (pos) { // setter.
      if (pos.x || pos.x === 0) {
        modPos('left', pos.x);
      }
      if (pos.y || pos.y === 0) { 
        modPos('top', pos.y);
      }
      return this;
    } else { // getter.
      return { 'x': modPos('left'), 'y': modPos('top') };
    }
	},
	getPos: function (obj) {
		var curleft = 0,
      curtop = 0;
		if (obj.offsetParent) {
			curleft = obj.offsetLeft;
			curtop = obj.offsetTop;
			while ((obj.offsetParent)) {
				obj = obj.offsetParent;
        curleft = curleft + obj.offsetLeft;
				curtop = curtop + obj.offsetTop;
			}
		}
		return { x: curleft, y: curtop };
	},
	getWindowInnerSize: function () {
    var x, y;
    if (typeof (window.innerWidth) === 'number') {
      // Standards compliant browsers.
      x = window.innerWidth;
      y = window.innerHeight;
    } else if (document.documentElement && (document.documentElement.clientWidth || document.documentElement.clientHeight)) {
      // IE 6-8 in 'standards compliant mode'.
      x = document.documentElement.clientWidth;
      y = document.documentElement.clientHeight;
    } else if (document.body && (document.body.clientWidth || document.body.clientHeight)) {
      // IE 4 and IE 5-8 in 'quirks mode'.
      x = document.body.clientWidth;
      y = document.body.clientHeight;
    }
    return { 'x': x, 'y': y };
  },
	getScrollPosition: function () {
    var x = 0, y = 0;
    if (document.body && (document.body.scrollLeft || document.body.scrollTop)) {
      // Standards compliant browsers.
      x = document.body.scrollLeft;
      y = document.body.scrollTop;
    } else if (document.documentElement && 
        (document.documentElement.scrollLeft || document.documentElement.scrollTop)) {
      // IE 6.
      x = document.documentElement.scrollLeft;
      y = document.documentElement.scrollTop;
    }
    return { 'x': x, 'y': y };
  },
  getStyle: function (element, style) { // based on getStyle in Prototype 1.5
		var css, value = element.style[style];
    style = CARPE.camelize(style);
		if (!value) {
			if (document.defaultView && document.defaultView.getComputedStyle) {
				css = document.defaultView.getComputedStyle(element, null);
				value = css ? css[style] : null;
			} else if (element.currentStyle) {
				value = element.currentStyle[style];
			}
		}
		return value;
	},
	camelize: function (s) {
		var parts = s.split('-'),
      camel,
      len,
      i;
		if (parts.length === 1) {
      return parts[0];
    }
		camel = parts[0];
		len = parts.length;
		for (i = 1; i < len; i = i + 1) {
			camel = camel + parts[i].charAt(0).toUpperCase() + parts[i].substring(1);
		}
	  return camel;
	},
	stopEvent: function (e) {
		e = e || window.event;
    if (e) {
      if (e && e.preventDefault) {
        e.preventDefault();
        e.stopPropagation();
      } else {
        e.returnValue = false;
        e.cancelBubble = true;
      }
      return true;
    } else {
      return false;
    }
	},
	addEventListener: (function () {
    function add (obj, type, func) {
      obj.addEventListener(type, func, false);
    }
    if (!window.addEventListener && window.document.attachEvent) {
      add = function (obj, type, func) {
        obj.attachEvent('on' + type, func);
      }
    }
    return add;
  })(),
	removeEventListener: (function () {
    function remove (obj, type, func) {
      obj.removeEventListener(type, func, false);
    };
    if (window.document.detachEvent) {
      function remove (obj, type, func) {
        if (obj.detachEvent) {
          obj.detachEvent('on' + type, func);
        }
      }
    }
    return remove;
  })(),
	listObj: function (o) {
		var s = '',
      i;
		for (i in o) {
			s = s + i + ': ' + o.toString() + ',';
		}
		window.alert(s);
	},
  roundToDec: function (num, decimals) {
    var n = num || 0;
    if (decimals < 0) {
      return n; 
    }
    helper = Math.pow(10, decimals);
    return Math.round(n * helper) / helper;
  },
  domLoaded: function (callback) {
    // Mozilla, Chrome, Opera:
    if (document.addEventListener) {
        document.addEventListener('DOMContentLoaded', callback, false);
    }
    // Safari, iCab, Konqueror:
    if (/KHTML|WebKit|iCab/i.test(navigator.userAgent)) {
        var DOMLoadTimer = setInterval(function () {
            if (/loaded|complete/i.test(document.readyState)) {
                callback();
                clearInterval(DOMLoadTimer);
            }
        }, 10);
    }
    // Other web browsers:
    window.onload = callback;
  }
};
