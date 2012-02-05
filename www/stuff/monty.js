/* Monty Hall Paradox tester
http://en.wikipedia.org/wiki/Monty_Hall_problem

Suppose you're on a game show, and you're given the choice of three doors: Behind one door is a prize; behind the others, nothing. You pick a door, say No. 1 [but the door is not opened], and the host, who knows what's behind the doors, opens another door, say No. 3, which has nothing. He then says to you, "Do you want to pick door No. 2?" 
Is it to your advantage to switch your choice?

for node.js, http://nodejs.org/

run with:
node monty.js

http://dannyayers.com/stuff/monty.js
public domain - danny 2012-02-05
*/


/**
 ** random selection of integer 0, 1 or 2
 **/
function r() { 
	return Math.floor(Math.random() * 3);	
}

/**
 ** set up 3 doors (in an array), one of which has a prize behind it
 **/
function newDoors(){
	var doors = new Array(3); // 3 doors

	for(var i=0;i<3;i++){ 
		doors[i] = {prize:false, number:i}; // js idiom : create object with a property prize=false and its number 
	} // none have prizes
	doors[r()].prize = true; // now one of them has a prize
	return doors;
}

/**
 ** a contestant is tested 
 ** they have some doors and a decision whether or not to change their mind
 **/
function contestant(doors, stick) { 
	
	var chosen = doors[r(3)]; // contestant chooses a door at random

	var first = doors.pop(); // try opening a door... (get the last object in the array)

	while(first == chosen || first.prize){ // can't have that one, it's the one that was chosen/has prize, try next
		doors.unshift(first); // close it (unshift is push to the begining of the array)
		first = doors.pop(); //open the next door
	}


	// not in the rules - oops
	// if(first.prize) return false; // the prize is behind the opened door, not the one chosen - fail

	if(stick) { // the contestant sticks with their first choice
		return chosen.prize;
	}
	return !chosen.prize; // the contestant picks the other door (only two doors, one prize - it much be the opposite)
}

var stickerScore = 0, switcherScore = 0;

for(var i=0;i<1000000;i++) { // a million contestants!
	if(contestant(newDoors(), true)) stickerScore++;
	if(contestant(newDoors(), false)) switcherScore++;
}
console.log("Stick scored: "+stickerScore);
console.log("Switch scored: "+switcherScore);
	
	
	
	



	



	

		

	




