package edu.cmu.tartan.configuration;

import edu.cmu.tartan.Game;

public abstract class GameConfiguration {
    public abstract void configure(Game game) throws InvalidGameException;
    public String name;
};



//	public static void njit(Game game) {
//
//		String hallwayDesription = "You are in a hallway. \nThere are conference rooms to the W, bathrooms to the N, \nand a stairwell to the E. with a lock on the door";
//		String hallwayShortDescription = "Fourth floor hallway";
//
//		String hallway2Desription = "You are in the second floor hallway. This floor of the GITC seems pointless, as it is completely desolate and have never done anything here ever. There is the stairwell to the East, and the elevator to the North.";
//		String hallway2ShortDescription = "Second floor hallway";
//
//		String hallway1Desription = "You are in the first floor lobby. There is the stairwell to the West, and the elevators to the North.";
//		String hallway1ShortDescription = "First floor lobby";
//
//		String conferenceRoomDescription = "You are in the conference room. You think back to all of the successful ACM meetings you had here.";
//		String conferenceRoomShortDescription = "Conference room";
//
//		String stairwellDescription = "You are in the stairwell. There is a couple making out, and you stand there awkwardly watching. You can go up, down, or back to the West.";
//		String stairwellShortDescription = "Fourth floorStairwell";
//
//		String stairwell3Description = "You are in the third floor stairwell. It is very quiet. The door leading to the third floor hallway is blocked.";
//		String stairwell3ShortDescription = "Third floor Stairwell";
//
//		String stairwell2Description = "You are in the second floor stairwell. You can go up, down, or to the second floor hallway to the West";
//		String stairwell2ShortDescription = "Second floor Stairwell";
//
//		String stairwell1Description = "You are in the first floor stairwell. You can go up, down, or to the first floor lobby to the West";
//		String stairwell1ShortDescription = "First floor Stairwell";
//
//		String roofDescription = "You are on the roof. There is a police chopper circling overhead.\nYou can head back downstairs, or make a jump for it to the adjacent roof to the E.";
//		String roofShortDescription = "GITC Rooftop";
//
//		String dirtRoomDescription = "Giant pile of dirt.";
//		String dirtRoomShortDescription = "Dirt pile";
//		String dirtRoomRevealMessage = "You have revealed a clay pot.";
//
//		String elevatorDescription = "GITC Elevator";
//		String elevatorShortDescription = "Elevator";
//
//		String floor1Description = "First floor of GITC. There is an elevator to the West, and the first floor lobby to the South.";
//		String floor2Description = "Second floor Lobby. There is an elevator to the West, and the second floor hallway to the South.";
//		String floor3Description = "Third floor Lobby. There is an elevator to the West";
//		String floor4Description = "Fourth floor Lobby of GITC. There is an elevator to the West, and the fourth floor hallway to the South.";
//		String floor1ShortDescription = "First floor Elevator Front.";
//		String floor2ShortDescription = "Second floor Elevator Front";
//		String floor3ShortDescription = "Third floor Elevator Front";
//		String floor4ShortDescription = "Fourth Floor Elevator Front";
//
//		String gitcExteriorDescription = "You are outside the GITC building. You are blocked in because of construction purposes. The GITC is to the North";
//		String gitcExteriorShortDescription = "GITC Exterior";
//
//
//		String bathroomDescription = "You are in the 4th floor bathroom. You are overcome by the stench of a disgusting turd that was once laid. There is grafitti on the wall...";
//		String bathroomShortDescription = "Bathroom";
//
//		String passageDescription = "You are in a dark corridor dimly lit by torches. There are portaits of past ACM Presidents, such as Akhil and Ben Slepp. You can hear the distance sound of running water.";
//		String passageShortDescription = "Dark Corridor.";
//
//		String corridorDescription = "Well lit corridor. The sound of running water is getting louder.";
//		String corridorShortDescription = "Well lit corridor";
//		String corridorDarkDescription = "The light of the torches cannot reach here. It is completely pitch black. The sound of running water is getting louder.";
//		String corridorDarkShortDescription = "Pitch black corridor";
//
//		String waterslideDescription = "You have found the source of the running water! It is the waterslide that Ben Slepp promised! You can go down it if you want, but its destination is unknown";
//		String waterslideShortDescription = "Waterslide";
//
//		String acmDescription = "You are in the ACM office. There is a mysterious hole in the floor, with a sign next to it that says, \"Put valuables in here to score points!\".";
//		String acmShortDescription = "ACM Office";
//		String acmDarkDescription = "It is dark. Perhaps you can find a way to see...";
//		String acmDarkShortDescription = "Darkness";
//
//		Room hallway = new Room(hallwayDesription, hallwayShortDescription);
//		Room hallway2 = new Room(hallway2Desription, hallway2ShortDescription);
//		Room hallway1 = new Room(hallway1Desription, hallway1ShortDescription);
//
//		Room conference = new Room(conferenceRoomDescription, conferenceRoomShortDescription);
//		RoomLockable stairwell = new RoomLockable(stairwellDescription, stairwellShortDescription, true, Item.getInstance("key"));
//		Room stairwell3 = new Room(stairwell3Description, stairwell3ShortDescription);
//		stairwell3.setAdjacentRoom(Action.ActionGoUp, stairwell);
//		Room stairwell2 = new Room(stairwell2Description, stairwell2ShortDescription);
//		stairwell2.setAdjacentRoom(Action.ActionGoUp, stairwell3);
//		stairwell2.setAdjacentRoom(Action.ActionGoWest, hallway2);
//		Room stairwell1 = new Room(stairwell1Description, stairwell1ShortDescription);
//		stairwell1.setAdjacentRoom(Action.ActionGoUp, stairwell2);
//		stairwell1.setAdjacentRoom(Action.ActionGoEast, hallway1);
//
//		Item lock = Item.getInstance("lock");
//		lock.setRelatedRoom(stairwell);
//		hallway.putItem(lock);
//
//		Room roof = new Room(roofDescription, roofShortDescription);
//		roof.setAdjacentRoomTransitionMessage("You miss the roof and fall through the floor. Since everyone is using their headphones, nobody seems to notice.", Action.ActionGoEast);
//		roof.setAdjacentRoom(Action.ActionGoDown, stairwell);
//
//		RoomExcavatable dirt = new RoomExcavatable(dirtRoomDescription, dirtRoomShortDescription, dirtRoomRevealMessage);
//		LinkedList<Item> revealableItems = new LinkedList<Item>();
//		ItemClayPot i = (ItemClayPot)Item.getInstance("pot");
//		i.install(Item.getInstance("diamond"));
//		i.setDestroyMessage("You revealed a beautiful diamond!");
//		i.setDisappears(true);
//		revealableItems.add(i);
//		dirt.setRevealableItems(revealableItems);
//
//		RoomElevator elevator = new RoomElevator(elevatorDescription, elevatorShortDescription);
//		elevator.putItem(Item.getInstance("1"));
//		elevator.putItem(Item.getInstance("2"));
//		elevator.putItem(Item.getInstance("3"));
//		elevator.putItem(Item.getInstance("4"));
//
//		Room floor1 = new Room(floor1Description, floor1ShortDescription);
//		Item b1 = Item.getInstance("button");
//
//		b1.setRelatedRoom(elevator);
//		floor1.putItem(b1);
//		floor1.setAdjacentRoom(Action.ActionGoSouth, hallway1);
//		Room floor2 = new Room(floor2Description, floor2ShortDescription);
//		Item b2 = Item.getInstance("button");
//
//		b2.setRelatedRoom(elevator);
//		floor2.putItem(b2);
//		floor2.setAdjacentRoom(Action.ActionGoSouth, hallway2);
//
//		Room floor3 = new Room(floor3Description, floor3ShortDescription);
//
//		Item b3 = Item.getInstance("button");
//
//		b3.setRelatedRoom(elevator);
//		floor3.putItem(b3);
//		// restricted floor
//
//		Room floor4 = new Room(floor4Description, floor4ShortDescription);
//
//		Item b4 = Item.getInstance("button");
//
//		b4.setRelatedRoom(elevator);
//		floor4.putItem(b4);
//		floor4.setAdjacentRoom(Action.ActionGoSouth, dirt);
//
//		ArrayList<Room> list = new ArrayList<Room>();
//		list.add(floor1);
//		list.add(floor2);
//		list.add(floor3);
//		list.add(floor4);
//
//		ArrayList<String> descriptions = new ArrayList<String>();
//		descriptions.add("Inside Elevator -- floor 1.");
//		descriptions.add("Inside Elevator -- floor 2");
//		descriptions.add("Inside Elevator -- floor 3");
//		descriptions.add("Inside Elevator -- floor 4");
//
//		elevator.setFloors(descriptions, list, Action.ActionGoEast, 1);
//		ArrayList<Integer> restrictedFloors = new ArrayList<Integer>();
//		restrictedFloors.add(2);
//		elevator.setRestrictedFloors(restrictedFloors);
//
//
//		Room gitcExterior = new Room(gitcExteriorDescription, gitcExteriorShortDescription);
//		gitcExterior.setAdjacentRoom(Action.ActionGoNorth, hallway1);
//		ItemGold gold = (ItemGold)Item.getInstance("gold");
//		gold.setValue(10);
//		gitcExterior.putItem(gold);
//
//
//		Room bathroom = new Room(bathroomDescription, bathroomShortDescription);
//		hallway.setAdjacentRoom(Action.ActionGoEast, stairwell);
//		hallway.setAdjacentRoom(Action.ActionGoWest, conference);
//		hallway.setAdjacentRoom(Action.ActionGoNorth, bathroom);
//		hallway.setAdjacentRoom(Action.ActionGoNortheast, dirt);
//
//		bathroom.putItem(Item.getInstance("grafitti"));
//
//		Item fridge = Item.getInstance("fridge");
//		RoomObscured passage = new RoomObscured(passageDescription ,passageShortDescription, fridge);
//		passage.setObscureMessage("You have revealed a secret passage to the east!");
//		passage.putItem(Item.getInstance("torch"));
//		fridge.setRelatedRoom(passage);
//
//		RoomDark corridor = new RoomDark(corridorDescription, corridorShortDescription, corridorDarkDescription, corridorDarkShortDescription, true);
//		corridor.setSafeDirection(Action.ActionGoWest);
//		corridor.setDeathMessage("As you take your first step within the dark room, you trip on a mysterious object. You fall toward the floor, and hit your head against a large rock.");
//		corridor.setAdjacentRoomTransitionMessage("You proceed back toward the dim light of the dim torches", Action.ActionGoWest);
//
//		Room waterslide = new Room(waterslideDescription, waterslideShortDescription);
//		waterslide.setAdjacentRoomTransitionMessageWithDelay("Wee!!", Action.ActionGoDown, 1000);
//
//		waterslide.setOneWayAdjacentRoom(Action.ActionGoDown, floor1);
//		waterslide.setAdjacentRoom(Action.ActionGoWest, corridor);
//		passage.setAdjacentRoom(Action.ActionGoEast, corridor);
//
//		LinkedList<Item> items = new LinkedList<>();
//		//items.add(Item.ItemLightSwitch);
//		items.add(Item.getInstance("flashlight"));
//		items.add(Item.getInstance("key"));
//		items.add(Item.getInstance("shovel"));
//		items.add(Item.getInstance("hole"));
//		ItemRMS wax = (ItemRMS)Item.getInstance("rms");
//		ItemDiamond diamond = (ItemDiamond)Item.getInstance("diamond");
//		diamond.setValue(100);
//		wax.setMeltItem(diamond);
//		items.add(wax);
//		items.add(Item.getInstance("microwave"));
//		fridge.setInspectMessage("It appears to be leaking from the bottom. Perhaps you should get a better lookAround.");
//		items.add(fridge);
//		RoomDark acm = new RoomDark(acmDescription, acmShortDescription, acmDarkDescription, acmDarkShortDescription, false);
//		acm.putItems(items);
//		acm.setAdjacentRoom(Action.ActionGoNorth, hallway);
//		acm.setAdjacentRoom(Action.ActionGoEast, passage);
//		acm.setDeathMessage("As you take your first step within the dark room, you trip on a mysterious object. You fall toward the floor, and hit your head against a large rock.");
//
//		roof.setOneWayAdjacentRoom(Action.ActionGoEast, acm);
//
//		Player player = new Player(acm);
//		game.setPlayer(player);
//		return;
//	}


