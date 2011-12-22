        var regular = "x-small";

        function bigger(thing){
            //D'abord l'objet lui-me^me
            thing.style.fontSize = "large";
	
            //Ensuite son plus proche pre'de'cesseur <span>
            siblingElement = thing.previousSibling;
            while(siblingElement) {
				if(siblingElement.tagName=="SPAN") {
                    siblingElement.style.fontSize = "medium";
                    break;
				}
				siblingElement = siblingElement.previousSibling;
            }

            //Ensuite son plus proche successeur <span>
            siblingElement = thing.nextSibling;
            while(siblingElement) {
				if(siblingElement.tagName=="SPAN") {
                    siblingElement.style.fontSize = "medium";
                    break;
				}
                siblingElement = siblingElement.nextSibling;
            }
        }//bigger

        function normal(thing){ //me^me jeu
            thing.style.fontSize = regular;

            siblingElement = thing.previousSibling;
            while(siblingElement) {
				if(siblingElement.tagName=="SPAN") {
                    siblingElement.style.fontSize = regular;
                    break;
				}
				siblingElement = siblingElement.previousSibling;
            }

            siblingElement = thing.nextSibling;
            while(siblingElement) {
				if(siblingElement.tagName=="SPAN") {
                    siblingElement.style.fontSize = regular;
                    break;
			}
			siblingElement = siblingElement.nextSibling;
		}
    }
