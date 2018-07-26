package edu.cmu.tartan.test;

import static org.junit.jupiter.api.Assertions.*;

//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runners.MethodSorters;


import org.junit.jupiter.api.Test;

class TestRandom {
	
	void assertTrue(String message, boolean bool) {
		org.junit.jupiter.api.Assertions.assertTrue(bool, message);
	}

    public static boolean debug = false;

    @Test
    public void test01() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test01");
        edu.cmu.tartan.games.LockRoomGame lockRoomGame0 = new edu.cmu.tartan.games.LockRoomGame();
    }

    @Test
    public void test02() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test02");
        edu.cmu.tartan.xml.XmlWriter.saveXmlStringToFile("hi!", "hi!");
    }

    @Test
    public void test03() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test03");
        edu.cmu.tartan.games.CollectGame collectGame0 = new edu.cmu.tartan.games.CollectGame();
        edu.cmu.tartan.Game game1 = null;
        try {
            collectGame0.configure(game1);
            fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
        }
    }

    @Test
    public void test04() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test04");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_ACQUIRE;
        assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_ACQUIRE + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_ACQUIRE));
    }

    @Test
    public void test05() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test05");
        edu.cmu.tartan.Main main0 = new edu.cmu.tartan.Main();
    }

    @Test
    public void test06() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test06");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_CLIMB;
        assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_CLIMB + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_CLIMB));
    }

    @Test
    public void test07() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test07");
        java.lang.String[] strArray6 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick7 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray6);
        java.lang.String[] strArray8 = itemBrick7.getAliases();
        edu.cmu.tartan.item.ItemVendingMachine itemVendingMachine9 = new edu.cmu.tartan.item.ItemVendingMachine("hi!", "hi!", strArray8);
        boolean boolean10 = itemVendingMachine9.accident();
        assertNotNull(strArray6);
        assertNotNull(strArray8);
        assertTrue("'" + boolean10 + "' != '" + false + "'", boolean10 == false);
    }

    @Test
    public void test08() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test08");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray1 = action0.getAliases();
        edu.cmu.tartan.action.Action action2 = action0.getOppositeDirection();
        assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray1);
        assertNull(action2);
    }

    @Test
    public void test09() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test09");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_PASS;
        assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_PASS + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_PASS));
    }

    @Test
    public void test10() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test10");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_GO_UP;
        assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_GO_UP + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_GO_UP));
    }

    @Test
    public void test11() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test11");
        edu.cmu.tartan.socket.ISocketHandler iSocketHandler0 = null;
        edu.cmu.tartan.manager.IQueueHandler iQueueHandler1 = null;
        edu.cmu.tartan.manager.TartanGameManager tartanGameManager2 = new edu.cmu.tartan.manager.TartanGameManager(iSocketHandler0, iQueueHandler1);
        boolean boolean5 = tartanGameManager2.registerNewGame("", "");
        assertTrue("'" + boolean5 + "' != '" + false + "'", boolean5 == false);
    }

    @Test
    public void test12() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test12");
        edu.cmu.tartan.action.Type type0 = edu.cmu.tartan.action.Type.TYPE_HASINDIRECTOBJECT;
        assertTrue("'" + type0 + "' != '" + edu.cmu.tartan.action.Type.TYPE_HASINDIRECTOBJECT + "'", type0.equals(edu.cmu.tartan.action.Type.TYPE_HASINDIRECTOBJECT));
    }

/*    @Test
    public void test13() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test13");
        edu.cmu.tartan.GameInterface gameInterface0 = null;
        try {
            edu.cmu.tartan.GameInterface.GameInterfaceFormatter gameInterfaceFormatter1 = gameInterface0.new GameInterfaceFormatter();
            fail("Expected exception of type java.lang.NullPointerException; message: reflection call to edu.cmu.tartan.GameInterface$GameInterfaceFormatter with null for superclass argument");
        } catch (java.lang.NullPointerException e) {
        }
    }*/

    @Test
    public void test14() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test14");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_GO_DOWN;
        assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_GO_DOWN + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_GO_DOWN));
    }

    @Test
    public void test15() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test15");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_DIG;
        assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_DIG + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_DIG));
    }

    @Test
    public void test16() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test16");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_ENABLE;
        assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_ENABLE + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_ENABLE));
    }

    @Test
    public void test17() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test17");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_GO_SOUTHWEST;
        assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_GO_SOUTHWEST + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_GO_SOUTHWEST));
    }

    @Test
    public void test18() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test18");
        edu.cmu.tartan.action.Action action2 = edu.cmu.tartan.action.Action.ACTION_TAKE;
        java.lang.String[] strArray3 = action2.getAliases();
        edu.cmu.tartan.item.ItemDynamite itemDynamite4 = new edu.cmu.tartan.item.ItemDynamite("", "", strArray3);
        java.lang.Boolean boolean5 = itemDynamite4.getExploded();
        edu.cmu.tartan.action.Action action8 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray9 = action8.getAliases();
        edu.cmu.tartan.item.ItemFood itemFood10 = new edu.cmu.tartan.item.ItemFood("hi!", "", strArray9);
        java.lang.String str11 = itemFood10.description();
        boolean boolean12 = itemDynamite4.equals((java.lang.Object) str11);
        assertTrue("'" + action2 + "' != '" + edu.cmu.tartan.action.Action.ACTION_TAKE + "'", action2.equals(edu.cmu.tartan.action.Action.ACTION_TAKE));
        assertNotNull(strArray3);
        assertTrue("'" + boolean5 + "' != '" + false + "'", boolean5.equals(false));
        assertTrue("'" + action8 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action8.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray9);
        assertTrue("'" + str11 + "' != '" + "hi!" + "'", str11.equals("hi!"));
        assertTrue("'" + boolean12 + "' != '" + false + "'", boolean12 == false);
    }

    @Test
    public void test19() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test19");
        edu.cmu.tartan.room.RoomObscured roomObscured2 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        edu.cmu.tartan.action.Action action3 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        boolean boolean4 = roomObscured2.canMoveToRoomInDirection(action3);
        assertTrue("'" + action3 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action3.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertTrue("'" + boolean4 + "' != '" + false + "'", boolean4 == false);
    }

    @Test
    public void test20() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test20");
        edu.cmu.tartan.room.RoomObscured roomObscured2 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        edu.cmu.tartan.action.Action action3 = edu.cmu.tartan.action.Action.ACTION_TAKE;
        edu.cmu.tartan.action.Type type4 = action3.type();
        edu.cmu.tartan.room.Room room5 = roomObscured2.getRoomForDirection(action3);
        try {
            java.lang.String str6 = room5.visibleItems();
            fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
        }
        assertTrue("'" + action3 + "' != '" + edu.cmu.tartan.action.Action.ACTION_TAKE + "'", action3.equals(edu.cmu.tartan.action.Action.ACTION_TAKE));
        assertTrue("'" + type4 + "' != '" + edu.cmu.tartan.action.Type.TYPE_HASINDIRECTOBJECT + "'", type4.equals(edu.cmu.tartan.action.Type.TYPE_HASINDIRECTOBJECT));
        assertNull(room5);
    }

    @Test
    public void test21() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test21");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_EAT;
        assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EAT + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_EAT));
    }

    @Test
    public void test22() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test22");
        edu.cmu.tartan.item.Item item1 = edu.cmu.tartan.item.Item.getInstance("");
        assertNull(item1);
    }

    @Test
    public void test24() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test24");
        edu.cmu.tartan.action.Type type0 = edu.cmu.tartan.action.Type.TYPE_HASNOOBJECT;
        assertTrue("'" + type0 + "' != '" + edu.cmu.tartan.action.Type.TYPE_HASNOOBJECT + "'", type0.equals(edu.cmu.tartan.action.Type.TYPE_HASNOOBJECT));
    }

    @Test
    public void test25() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test25");
        edu.cmu.tartan.goal.DemoGoal demoGoal0 = new edu.cmu.tartan.goal.DemoGoal();
        java.lang.Boolean boolean1 = demoGoal0.isAchieved();
        assertTrue("'" + boolean1 + "' != '" + false + "'", boolean1.equals(false));
    }

    @Test
    public void test26() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test26");
        java.lang.String[] strArray6 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick7 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray6);
        edu.cmu.tartan.item.ItemClayPot itemClayPot8 = new edu.cmu.tartan.item.ItemClayPot("hi!", "", strArray6);
        itemClayPot8.setDestroyMessage("hi!");
        edu.cmu.tartan.action.Action action15 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray16 = action15.getAliases();
        edu.cmu.tartan.item.ItemFood itemFood17 = new edu.cmu.tartan.item.ItemFood("hi!", "", strArray16);
        edu.cmu.tartan.item.ItemMicrowave itemMicrowave18 = new edu.cmu.tartan.item.ItemMicrowave("hi!", "", strArray16);
        edu.cmu.tartan.action.ActionExecutionUnit actionExecutionUnit19 = new edu.cmu.tartan.action.ActionExecutionUnit((edu.cmu.tartan.item.Item) itemClayPot8, (edu.cmu.tartan.item.Item) itemMicrowave18);
        edu.cmu.tartan.item.Item item20 = itemMicrowave18.installedItem();
        assertNotNull(strArray6);
        assertTrue("'" + action15 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action15.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray16);
        assertNull(item20);
    }

    @Test
    public void test27() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test27");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_THROW;
        assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_THROW + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_THROW));
    }

    @Test
    public void test28() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test28");
        java.io.Closeable closeable0 = null;
        edu.cmu.tartan.xml.XmlWriter.close(closeable0);
    }

    @Test
    public void test29() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test29");
        java.lang.String[] strArray6 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick7 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray6);
        java.lang.String[] strArray8 = itemBrick7.getAliases();
        edu.cmu.tartan.item.ItemVendingMachine itemVendingMachine9 = new edu.cmu.tartan.item.ItemVendingMachine("hi!", "hi!", strArray8);
        itemVendingMachine9.shake();
        assertNotNull(strArray6);
        assertNotNull(strArray8);
    }

    @Test
    public void test30() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test30");
        edu.cmu.tartan.room.RoomObscured roomObscured2 = new edu.cmu.tartan.room.RoomObscured("hi!", "");
        edu.cmu.tartan.room.RoomObscured roomObscured6 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        edu.cmu.tartan.action.Action action7 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        edu.cmu.tartan.room.RoomObscured roomObscured10 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        roomObscured6.setOneWayAdjacentRoom(action7, (edu.cmu.tartan.room.Room) roomObscured10);
        roomObscured2.setAdjacentRoomTransitionMessageWithDelay("hi!", action7, (int) '4');
        edu.cmu.tartan.action.Action action16 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray17 = action16.getAliases();
        edu.cmu.tartan.item.ItemFood itemFood18 = new edu.cmu.tartan.item.ItemFood("hi!", "", strArray17);
        java.lang.String str19 = itemFood18.description();
        edu.cmu.tartan.action.Action action24 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray25 = action24.getAliases();
        edu.cmu.tartan.item.ItemFood itemFood26 = new edu.cmu.tartan.item.ItemFood("hi!", "", strArray25);
        edu.cmu.tartan.item.ItemMicrowave itemMicrowave27 = new edu.cmu.tartan.item.ItemMicrowave("hi!", "", strArray25);
        edu.cmu.tartan.item.Item[] itemArray28 = new edu.cmu.tartan.item.Item[] { itemFood18, itemMicrowave27 };
        java.util.ArrayList<edu.cmu.tartan.item.Item> itemList29 = new java.util.ArrayList<edu.cmu.tartan.item.Item>();
        boolean boolean30 = java.util.Collections.addAll((java.util.Collection<edu.cmu.tartan.item.Item>) itemList29, itemArray28);
        edu.cmu.tartan.Player player31 = new edu.cmu.tartan.Player((edu.cmu.tartan.room.Room) roomObscured2, (java.util.List<edu.cmu.tartan.item.Item>) itemList29);
        edu.cmu.tartan.room.Room room32 = player31.currentRoom();
        assertTrue("'" + action7 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action7.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertTrue("'" + action16 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action16.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray17);
        assertTrue("'" + str19 + "' != '" + "hi!" + "'", str19.equals("hi!"));
        assertTrue("'" + action24 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action24.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray25);
        assertNotNull(itemArray28);
        assertTrue("'" + boolean30 + "' != '" + true + "'", boolean30 == true);
        assertNotNull(room32);
    }

    @Test
    public void test31() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test31");
        edu.cmu.tartan.games.CustomizingGame customizingGame0 = new edu.cmu.tartan.games.CustomizingGame();
        java.util.List<java.lang.String> strList1 = null;
        edu.cmu.tartan.Player player2 = null;
        edu.cmu.tartan.goal.GameExploreGoal gameExploreGoal3 = new edu.cmu.tartan.goal.GameExploreGoal(strList1, player2);
        customizingGame0.addGoal((edu.cmu.tartan.goal.GameGoal) gameExploreGoal3);
        edu.cmu.tartan.Game game5 = null;
        try {
            customizingGame0.configure(game5);
            fail("Expected exception of type java.lang.IndexOutOfBoundsException; message: Index: 0, Size: 0");
        } catch (java.lang.IndexOutOfBoundsException e) {
        }
    }

    @Test
    public void test32() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test32");
        java.lang.String[] strArray7 = new java.lang.String[] { "hi!", "", "hi!", "", "" };
        edu.cmu.tartan.item.ItemDiamond itemDiamond8 = new edu.cmu.tartan.item.ItemDiamond("hi!", "", strArray7);
        assertNotNull(strArray7);
    }

    @Test
    public void test33() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test33");
        edu.cmu.tartan.room.RoomObscured roomObscured2 = new edu.cmu.tartan.room.RoomObscured("hi!", "");
        edu.cmu.tartan.room.RoomObscured roomObscured6 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        edu.cmu.tartan.action.Action action7 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        edu.cmu.tartan.room.RoomObscured roomObscured10 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        roomObscured6.setOneWayAdjacentRoom(action7, (edu.cmu.tartan.room.Room) roomObscured10);
        roomObscured2.setAdjacentRoomTransitionMessageWithDelay("hi!", action7, (int) '4');
        edu.cmu.tartan.action.Action action16 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray17 = action16.getAliases();
        edu.cmu.tartan.item.ItemFood itemFood18 = new edu.cmu.tartan.item.ItemFood("hi!", "", strArray17);
        java.lang.String str19 = itemFood18.description();
        edu.cmu.tartan.action.Action action24 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray25 = action24.getAliases();
        edu.cmu.tartan.item.ItemFood itemFood26 = new edu.cmu.tartan.item.ItemFood("hi!", "", strArray25);
        edu.cmu.tartan.item.ItemMicrowave itemMicrowave27 = new edu.cmu.tartan.item.ItemMicrowave("hi!", "", strArray25);
        edu.cmu.tartan.item.Item[] itemArray28 = new edu.cmu.tartan.item.Item[] { itemFood18, itemMicrowave27 };
        java.util.ArrayList<edu.cmu.tartan.item.Item> itemList29 = new java.util.ArrayList<edu.cmu.tartan.item.Item>();
        boolean boolean30 = java.util.Collections.addAll((java.util.Collection<edu.cmu.tartan.item.Item>) itemList29, itemArray28);
        edu.cmu.tartan.Player player31 = new edu.cmu.tartan.Player((edu.cmu.tartan.room.Room) roomObscured2, (java.util.List<edu.cmu.tartan.item.Item>) itemList29);
        java.lang.String[] strArray38 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick39 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray38);
        java.lang.String[] strArray40 = itemBrick39.getAliases();
        edu.cmu.tartan.item.ItemVendingMachine itemVendingMachine41 = new edu.cmu.tartan.item.ItemVendingMachine("hi!", "hi!", strArray40);
        java.lang.String[] strArray46 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick47 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray46);
        java.lang.String[] strArray48 = itemBrick47.getAliases();
        itemBrick47.setVisible(false);
        edu.cmu.tartan.action.ActionExecutionUnit actionExecutionUnit51 = new edu.cmu.tartan.action.ActionExecutionUnit((edu.cmu.tartan.item.Item) itemVendingMachine41, (edu.cmu.tartan.item.Item) itemBrick47);
        player31.score((edu.cmu.tartan.properties.Valuable) itemVendingMachine41);
        int int53 = player31.getPossiblePoints();
        assertTrue("'" + action7 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action7.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertTrue("'" + action16 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action16.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray17);
        assertTrue("'" + str19 + "' != '" + "hi!" + "'", str19.equals("hi!"));
        assertTrue("'" + action24 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action24.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray25);
        assertNotNull(itemArray28);
        assertTrue("'" + boolean30 + "' != '" + true + "'", boolean30 == true);
        assertNotNull(strArray38);
        assertNotNull(strArray40);
        assertNotNull(strArray46);
        assertNotNull(strArray48);
        assertTrue("'" + int53 + "' != '" + 0 + "'", int53 == 0);
    }

    @Test
    public void test35() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test35");
        edu.cmu.tartan.room.RoomObscured roomObscured2 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        edu.cmu.tartan.action.Action action3 = edu.cmu.tartan.action.Action.ACTION_TAKE;
        edu.cmu.tartan.action.Type type4 = action3.type();
        edu.cmu.tartan.room.Room room5 = roomObscured2.getRoomForDirection(action3);
        try {
            java.util.Map<edu.cmu.tartan.action.Action, java.lang.String> actionMap6 = room5.transitionMessages();
            fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
        }
        assertTrue("'" + action3 + "' != '" + edu.cmu.tartan.action.Action.ACTION_TAKE + "'", action3.equals(edu.cmu.tartan.action.Action.ACTION_TAKE));
        assertTrue("'" + type4 + "' != '" + edu.cmu.tartan.action.Type.TYPE_HASINDIRECTOBJECT + "'", type4.equals(edu.cmu.tartan.action.Type.TYPE_HASINDIRECTOBJECT));
        assertNull(room5);
    }

    @Test
    public void test36() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test36");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_INSPECT;
        assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_INSPECT + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_INSPECT));
    }

    @Test
    public void test37() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test37");
        edu.cmu.tartan.action.Action action2 = edu.cmu.tartan.action.Action.ACTION_TAKE;
        java.lang.String[] strArray3 = action2.getAliases();
        edu.cmu.tartan.item.ItemTorch itemTorch4 = new edu.cmu.tartan.item.ItemTorch("", "", strArray3);
        java.lang.String str5 = itemTorch4.description();
        assertTrue("'" + action2 + "' != '" + edu.cmu.tartan.action.Action.ACTION_TAKE + "'", action2.equals(edu.cmu.tartan.action.Action.ACTION_TAKE));
        assertNotNull(strArray3);
        assertTrue("'" + str5 + "' != '" + "" + "'", str5.equals(""));
    }

    @Test
    public void test38() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test38");
        java.lang.String[] strArray2 = new java.lang.String[] { "", "" };
        java.util.ArrayList<java.lang.String> strList3 = new java.util.ArrayList<java.lang.String>();
        boolean boolean4 = java.util.Collections.addAll((java.util.Collection<java.lang.String>) strList3, strArray2);
        edu.cmu.tartan.goal.GameExploreGoal gameExploreGoal5 = new edu.cmu.tartan.goal.GameExploreGoal((java.util.List<java.lang.String>) strList3);
        edu.cmu.tartan.room.RoomObscured roomObscured8 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        edu.cmu.tartan.action.Action action9 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        edu.cmu.tartan.room.RoomObscured roomObscured12 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        roomObscured8.setOneWayAdjacentRoom(action9, (edu.cmu.tartan.room.Room) roomObscured12);
        edu.cmu.tartan.Player player14 = new edu.cmu.tartan.Player((edu.cmu.tartan.room.Room) roomObscured12);
        gameExploreGoal5.setPlayer(player14);
        edu.cmu.tartan.item.Item item16 = null;
        java.lang.String[] strArray23 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick24 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray23);
        java.lang.String[] strArray25 = itemBrick24.getAliases();
        edu.cmu.tartan.item.ItemVendingMachine itemVendingMachine26 = new edu.cmu.tartan.item.ItemVendingMachine("hi!", "hi!", strArray25);
        java.lang.String[] strArray31 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick32 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray31);
        java.lang.String[] strArray33 = itemBrick32.getAliases();
        itemBrick32.setVisible(false);
        edu.cmu.tartan.action.ActionExecutionUnit actionExecutionUnit36 = new edu.cmu.tartan.action.ActionExecutionUnit((edu.cmu.tartan.item.Item) itemVendingMachine26, (edu.cmu.tartan.item.Item) itemBrick32);
        try {
            player14.putItemInItem(item16, (edu.cmu.tartan.item.Item) itemVendingMachine26);
            fail("Expected exception of type java.lang.ClassCastException; message: edu.cmu.tartan.item.ItemVendingMachine cannot be cast to edu.cmu.tartan.properties.Hostable");
        } catch (java.lang.ClassCastException e) {
        }
        assertNotNull(strArray2);
        assertTrue("'" + boolean4 + "' != '" + true + "'", boolean4 == true);
        assertTrue("'" + action9 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action9.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray23);
        assertNotNull(strArray25);
        assertNotNull(strArray31);
        assertNotNull(strArray33);
    }

    @Test
    public void test39() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test39");
        java.lang.String[] strArray2 = new java.lang.String[] { "", "" };
        java.util.ArrayList<java.lang.String> strList3 = new java.util.ArrayList<java.lang.String>();
        boolean boolean4 = java.util.Collections.addAll((java.util.Collection<java.lang.String>) strList3, strArray2);
        edu.cmu.tartan.goal.GameExploreGoal gameExploreGoal5 = new edu.cmu.tartan.goal.GameExploreGoal((java.util.List<java.lang.String>) strList3);
        edu.cmu.tartan.room.RoomObscured roomObscured8 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        edu.cmu.tartan.action.Action action9 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        edu.cmu.tartan.room.RoomObscured roomObscured12 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        roomObscured8.setOneWayAdjacentRoom(action9, (edu.cmu.tartan.room.Room) roomObscured12);
        edu.cmu.tartan.Player player14 = new edu.cmu.tartan.Player((edu.cmu.tartan.room.Room) roomObscured12);
        gameExploreGoal5.setPlayer(player14);
        boolean boolean16 = player14.hasLuminousItem();
        assertNotNull(strArray2);
        assertTrue("'" + boolean4 + "' != '" + true + "'", boolean4 == true);
        assertTrue("'" + action9 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action9.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertTrue("'" + boolean16 + "' != '" + false + "'", boolean16 == false);
    }

    @Test
    public void test40() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test40");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_GO_NORTH;
        assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_GO_NORTH + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_GO_NORTH));
    }

    @Test
    public void test41() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test41");
        edu.cmu.tartan.action.Action action4 = edu.cmu.tartan.action.Action.ACTION_TAKE;
        java.lang.String[] strArray5 = action4.getAliases();
        edu.cmu.tartan.item.ItemGold itemGold6 = new edu.cmu.tartan.item.ItemGold("", "", strArray5);
        edu.cmu.tartan.item.ItemUnknown itemUnknown7 = new edu.cmu.tartan.item.ItemUnknown("hi!", "", strArray5);
        assertTrue("'" + action4 + "' != '" + edu.cmu.tartan.action.Action.ACTION_TAKE + "'", action4.equals(edu.cmu.tartan.action.Action.ACTION_TAKE));
        assertNotNull(strArray5);
    }

    @Test
    public void test42() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test42");
        java.lang.String[] strArray6 = new java.lang.String[] { "hi!", "hi!", "", "hi!" };
        edu.cmu.tartan.item.ItemVendingMachine itemVendingMachine7 = new edu.cmu.tartan.item.ItemVendingMachine("hi!", "", strArray6);
        itemVendingMachine7.shake();
        assertNotNull(strArray6);
    }

    @Test
    public void test43() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test43");
        edu.cmu.tartan.room.RoomObscured roomObscured2 = new edu.cmu.tartan.room.RoomObscured("hi!", "");
        edu.cmu.tartan.room.RoomObscured roomObscured6 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        edu.cmu.tartan.action.Action action7 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        edu.cmu.tartan.room.RoomObscured roomObscured10 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        roomObscured6.setOneWayAdjacentRoom(action7, (edu.cmu.tartan.room.Room) roomObscured10);
        roomObscured2.setAdjacentRoomTransitionMessageWithDelay("hi!", action7, (int) '4');
        edu.cmu.tartan.action.Action action16 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray17 = action16.getAliases();
        edu.cmu.tartan.item.ItemFood itemFood18 = new edu.cmu.tartan.item.ItemFood("hi!", "", strArray17);
        java.lang.String str19 = itemFood18.description();
        edu.cmu.tartan.action.Action action24 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray25 = action24.getAliases();
        edu.cmu.tartan.item.ItemFood itemFood26 = new edu.cmu.tartan.item.ItemFood("hi!", "", strArray25);
        edu.cmu.tartan.item.ItemMicrowave itemMicrowave27 = new edu.cmu.tartan.item.ItemMicrowave("hi!", "", strArray25);
        edu.cmu.tartan.item.Item[] itemArray28 = new edu.cmu.tartan.item.Item[] { itemFood18, itemMicrowave27 };
        java.util.ArrayList<edu.cmu.tartan.item.Item> itemList29 = new java.util.ArrayList<edu.cmu.tartan.item.Item>();
        boolean boolean30 = java.util.Collections.addAll((java.util.Collection<edu.cmu.tartan.item.Item>) itemList29, itemArray28);
        edu.cmu.tartan.Player player31 = new edu.cmu.tartan.Player((edu.cmu.tartan.room.Room) roomObscured2, (java.util.List<edu.cmu.tartan.item.Item>) itemList29);
        java.util.Map<edu.cmu.tartan.action.Action, java.lang.String> actionMap32 = roomObscured2.transitionMessages();
        java.lang.String str33 = roomObscured2.visibleItems();
        assertTrue("'" + action7 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action7.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertTrue("'" + action16 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action16.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray17);
        assertTrue("'" + str19 + "' != '" + "hi!" + "'", str19.equals("hi!"));
        assertTrue("'" + action24 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action24.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray25);
        assertNotNull(itemArray28);
        assertTrue("'" + boolean30 + "' != '" + true + "'", boolean30 == true);
        assertNotNull(actionMap32);
        assertTrue("'" + str33 + "' != '" + "" + "'", str33.equals(""));
    }

    @Test
    public void test44() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test44");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_GO_NORTHWEST;
        assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_GO_NORTHWEST + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_GO_NORTHWEST));
    }

    @Test
    public void test45() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test45");
        java.net.Socket socket0 = null;
        edu.cmu.tartan.manager.IQueueHandler iQueueHandler1 = null;
        edu.cmu.tartan.socket.DesignerClientThread designerClientThread2 = new edu.cmu.tartan.socket.DesignerClientThread(socket0, iQueueHandler1);
        try {
            designerClientThread2.receiveMessage();
            fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
        }
    }

    @Test
    public void test46() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test46");
        java.lang.String[] strArray6 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick7 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray6);
        java.lang.String[] strArray8 = itemBrick7.getAliases();
        edu.cmu.tartan.item.ItemFolder itemFolder9 = new edu.cmu.tartan.item.ItemFolder("hi!", "", strArray8);
        boolean boolean11 = itemFolder9.equals((java.lang.Object) (byte) 10);
        itemFolder9.setVisible(true);
        assertNotNull(strArray6);
        assertNotNull(strArray8);
        assertTrue("'" + boolean11 + "' != '" + false + "'", boolean11 == false);
    }

    @Test
    public void test47() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test47");
        java.lang.String[] strArray6 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick7 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray6);
        edu.cmu.tartan.item.ItemClayPot itemClayPot8 = new edu.cmu.tartan.item.ItemClayPot("hi!", "", strArray6);
        edu.cmu.tartan.action.Action action13 = edu.cmu.tartan.action.Action.ACTION_TAKE;
        java.lang.String[] strArray14 = action13.getAliases();
        edu.cmu.tartan.item.ItemDynamite itemDynamite15 = new edu.cmu.tartan.item.ItemDynamite("", "", strArray14);
        edu.cmu.tartan.item.ItemDiamond itemDiamond16 = new edu.cmu.tartan.item.ItemDiamond("", "hi!", strArray14);
        boolean boolean17 = itemClayPot8.equals((java.lang.Object) strArray14);
        assertNotNull(strArray6);
        assertTrue("'" + action13 + "' != '" + edu.cmu.tartan.action.Action.ACTION_TAKE + "'", action13.equals(edu.cmu.tartan.action.Action.ACTION_TAKE));
        assertNotNull(strArray14);
        assertTrue("'" + boolean17 + "' != '" + false + "'", boolean17 == false);
    }

    @Test
    public void test48() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test48");
        edu.cmu.tartan.action.Action action4 = edu.cmu.tartan.action.Action.ACTION_TAKE;
        java.lang.String[] strArray5 = action4.getAliases();
        edu.cmu.tartan.item.ItemDynamite itemDynamite6 = new edu.cmu.tartan.item.ItemDynamite("", "", strArray5);
        edu.cmu.tartan.item.ItemBrick itemBrick7 = new edu.cmu.tartan.item.ItemBrick("hi!", "", strArray5);
        assertTrue("'" + action4 + "' != '" + edu.cmu.tartan.action.Action.ACTION_TAKE + "'", action4.equals(edu.cmu.tartan.action.Action.ACTION_TAKE));
        assertNotNull(strArray5);
    }

    @Test
    public void test49() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test49");
        java.lang.String[] strArray10 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick11 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray10);
        java.lang.String[] strArray12 = itemBrick11.getAliases();
        edu.cmu.tartan.item.ItemVendingMachine itemVendingMachine13 = new edu.cmu.tartan.item.ItemVendingMachine("hi!", "hi!", strArray12);
        edu.cmu.tartan.item.ItemShovel itemShovel14 = new edu.cmu.tartan.item.ItemShovel("hi!", "", strArray12);
        edu.cmu.tartan.item.ItemMicrowave itemMicrowave15 = new edu.cmu.tartan.item.ItemMicrowave("hi!", "hi!", strArray12);
        assertNotNull(strArray10);
        assertNotNull(strArray12);
    }

    @Test
    public void test50() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test50");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_GO_WEST;
        java.lang.String[] strArray1 = action0.getAliases();
        assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_GO_WEST + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_GO_WEST));
        assertNotNull(strArray1);
    }

    @Test
    public void test51() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test51");
        java.lang.String str0 = edu.cmu.tartan.GamePlayMessage.I_DO_NOT_SEE_THAT_HERE;
        assertTrue("'" + str0 + "' != '" + "I don't see that here." + "'", str0.equals("I don't see that here."));
    }

    @Test
    public void test52() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test52");
        java.net.Socket socket0 = null;
        edu.cmu.tartan.manager.IQueueHandler iQueueHandler1 = null;
        edu.cmu.tartan.socket.DesignerClientThread designerClientThread2 = new edu.cmu.tartan.socket.DesignerClientThread(socket0, iQueueHandler1);
        designerClientThread2.setIsLogin(false);
        try {
            designerClientThread2.receiveMessage();
            fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
        }
    }

    @Test
    public void test53() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test53");
        java.lang.String[] strArray6 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick7 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray6);
        edu.cmu.tartan.item.ItemClayPot itemClayPot8 = new edu.cmu.tartan.item.ItemClayPot("hi!", "", strArray6);
        itemClayPot8.setDestroyMessage("hi!");
        edu.cmu.tartan.action.Action action15 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray16 = action15.getAliases();
        edu.cmu.tartan.item.ItemFood itemFood17 = new edu.cmu.tartan.item.ItemFood("hi!", "", strArray16);
        edu.cmu.tartan.item.ItemMicrowave itemMicrowave18 = new edu.cmu.tartan.item.ItemMicrowave("hi!", "", strArray16);
        edu.cmu.tartan.action.ActionExecutionUnit actionExecutionUnit19 = new edu.cmu.tartan.action.ActionExecutionUnit((edu.cmu.tartan.item.Item) itemClayPot8, (edu.cmu.tartan.item.Item) itemMicrowave18);
        edu.cmu.tartan.room.RoomObscured roomObscured22 = new edu.cmu.tartan.room.RoomObscured("hi!", "");
        edu.cmu.tartan.room.RoomObscured roomObscured26 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        edu.cmu.tartan.action.Action action27 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        edu.cmu.tartan.room.RoomObscured roomObscured30 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        roomObscured26.setOneWayAdjacentRoom(action27, (edu.cmu.tartan.room.Room) roomObscured30);
        roomObscured22.setAdjacentRoomTransitionMessageWithDelay("hi!", action27, (int) '4');
        itemClayPot8.setRelatedRoom((edu.cmu.tartan.room.Room) roomObscured22);
        java.lang.String str35 = roomObscured22.visibleItems();
        java.lang.String[] strArray40 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick41 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray40);
        java.lang.String[] strArray42 = itemBrick41.getAliases();
        roomObscured22.setObscuringItem((edu.cmu.tartan.item.Item) itemBrick41);
        assertNotNull(strArray6);
        assertTrue("'" + action15 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action15.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray16);
        assertTrue("'" + action27 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action27.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertTrue("'" + str35 + "' != '" + "" + "'", str35.equals(""));
        assertNotNull(strArray40);
        assertNotNull(strArray42);
    }

    @Test
    public void test54() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test54");
        edu.cmu.tartan.xml.XmlResponseAddUser xmlResponseAddUser0 = new edu.cmu.tartan.xml.XmlResponseAddUser();
        org.w3c.dom.NodeList nodeList2 = null;
        try {
            java.lang.String str4 = xmlResponseAddUser0.getAttributeValueAtNthTag("I don't see that here.", nodeList2, (int) (byte) 100);
            fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
        }
    }

    @Test
    public void test55() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test55");
        edu.cmu.tartan.room.RoomObscured roomObscured2 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        edu.cmu.tartan.action.Action action3 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        edu.cmu.tartan.room.RoomObscured roomObscured6 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        roomObscured2.setOneWayAdjacentRoom(action3, (edu.cmu.tartan.room.Room) roomObscured6);
        edu.cmu.tartan.Player player8 = new edu.cmu.tartan.Player((edu.cmu.tartan.room.Room) roomObscured6);
        edu.cmu.tartan.action.Action action10 = edu.cmu.tartan.action.Action.ACTION_GO_WEST;
        roomObscured6.setAdjacentRoomTransitionMessageWithDelay("", action10, (int) ' ');
        assertTrue("'" + action3 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action3.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertTrue("'" + action10 + "' != '" + edu.cmu.tartan.action.Action.ACTION_GO_WEST + "'", action10.equals(edu.cmu.tartan.action.Action.ACTION_GO_WEST));
    }

    @Test
    public void test56() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test56");
        java.net.Socket socket0 = null;
        edu.cmu.tartan.manager.IQueueHandler iQueueHandler1 = null;
        edu.cmu.tartan.socket.DesignerClientThread designerClientThread2 = new edu.cmu.tartan.socket.DesignerClientThread(socket0, iQueueHandler1);
        try {
            designerClientThread2.run();
            fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
        }
    }

    @Test
    public void test57() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test57");
        java.lang.String[] strArray10 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick11 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray10);
        java.lang.String[] strArray12 = itemBrick11.getAliases();
        edu.cmu.tartan.item.ItemVendingMachine itemVendingMachine13 = new edu.cmu.tartan.item.ItemVendingMachine("hi!", "hi!", strArray12);
        edu.cmu.tartan.item.ItemShovel itemShovel14 = new edu.cmu.tartan.item.ItemShovel("hi!", "", strArray12);
        edu.cmu.tartan.item.ItemMagicBox itemMagicBox15 = new edu.cmu.tartan.item.ItemMagicBox("", "hi!", strArray12);
        edu.cmu.tartan.item.Item item16 = itemMagicBox15.installedItem();
        assertNotNull(strArray10);
        assertNotNull(strArray12);
        assertNull(item16);
    }

    @Test
    public void test58() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test58");
        java.lang.String[] strArray6 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick7 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray6);
        edu.cmu.tartan.item.ItemClayPot itemClayPot8 = new edu.cmu.tartan.item.ItemClayPot("hi!", "", strArray6);
        itemClayPot8.setDestroyMessage("hi!");
        edu.cmu.tartan.action.Action action15 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray16 = action15.getAliases();
        edu.cmu.tartan.item.ItemFood itemFood17 = new edu.cmu.tartan.item.ItemFood("hi!", "", strArray16);
        edu.cmu.tartan.item.ItemMicrowave itemMicrowave18 = new edu.cmu.tartan.item.ItemMicrowave("hi!", "", strArray16);
        edu.cmu.tartan.action.ActionExecutionUnit actionExecutionUnit19 = new edu.cmu.tartan.action.ActionExecutionUnit((edu.cmu.tartan.item.Item) itemClayPot8, (edu.cmu.tartan.item.Item) itemMicrowave18);
        edu.cmu.tartan.item.Item item20 = actionExecutionUnit19.directObject();
        assertNotNull(strArray6);
        assertTrue("'" + action15 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action15.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray16);
        assertNotNull(item20);
    }

    @Test
    public void test59() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test59");
        edu.cmu.tartan.goal.DemoGoal demoGoal0 = new edu.cmu.tartan.goal.DemoGoal();
        java.lang.String str1 = demoGoal0.describe();
        assertTrue("'" + str1 + "' != '" + "Demo goal" + "'", str1.equals("Demo goal"));
    }

    @Test
    public void test60() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test60");
        edu.cmu.tartan.xml.XmlParser xmlParser0 = new edu.cmu.tartan.xml.XmlParser();
        edu.cmu.tartan.GameConfiguration gameConfiguration2 = xmlParser0.processMessageReturnGameConfiguration("hi!");
        edu.cmu.tartan.GameConfiguration gameConfiguration4 = xmlParser0.processMessageReturnGameConfiguration("Demo goal");
        assertNull(gameConfiguration2);
        assertNull(gameConfiguration4);
    }

    @Test
    public void test61() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test61");
        java.lang.String[] strArray6 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick7 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray6);
        edu.cmu.tartan.item.ItemClayPot itemClayPot8 = new edu.cmu.tartan.item.ItemClayPot("hi!", "", strArray6);
        itemClayPot8.setDestroyMessage("hi!");
        edu.cmu.tartan.action.Action action15 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray16 = action15.getAliases();
        edu.cmu.tartan.item.ItemFood itemFood17 = new edu.cmu.tartan.item.ItemFood("hi!", "", strArray16);
        edu.cmu.tartan.item.ItemMicrowave itemMicrowave18 = new edu.cmu.tartan.item.ItemMicrowave("hi!", "", strArray16);
        edu.cmu.tartan.action.ActionExecutionUnit actionExecutionUnit19 = new edu.cmu.tartan.action.ActionExecutionUnit((edu.cmu.tartan.item.Item) itemClayPot8, (edu.cmu.tartan.item.Item) itemMicrowave18);
        itemClayPot8.setDisappears(false);
        assertNotNull(strArray6);
        assertTrue("'" + action15 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action15.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray16);
    }

/*    @Test
    public void test62() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test62");
        edu.cmu.tartan.Player player0 = null;
        edu.cmu.tartan.PlayerExecutionEngine playerExecutionEngine1 = new edu.cmu.tartan.PlayerExecutionEngine(player0);
        edu.cmu.tartan.action.Action action2 = edu.cmu.tartan.action.Action.ACTION_GO_NORTHEAST;
        edu.cmu.tartan.PlayerInterpreter playerInterpreter3 = new edu.cmu.tartan.PlayerInterpreter();
        edu.cmu.tartan.action.ActionExecutionUnit actionExecutionUnit5 = null;
        edu.cmu.tartan.action.Action action6 = playerInterpreter3.interpretString("hi!", actionExecutionUnit5);
        java.lang.String[] strArray14 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick15 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray14);
        edu.cmu.tartan.item.ItemClayPot itemClayPot16 = new edu.cmu.tartan.item.ItemClayPot("hi!", "", strArray14);
        itemClayPot16.setDestroyMessage("hi!");
        edu.cmu.tartan.action.Action action23 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray24 = action23.getAliases();
        edu.cmu.tartan.item.ItemFood itemFood25 = new edu.cmu.tartan.item.ItemFood("hi!", "", strArray24);
        edu.cmu.tartan.item.ItemMicrowave itemMicrowave26 = new edu.cmu.tartan.item.ItemMicrowave("hi!", "", strArray24);
        edu.cmu.tartan.action.ActionExecutionUnit actionExecutionUnit27 = new edu.cmu.tartan.action.ActionExecutionUnit((edu.cmu.tartan.item.Item) itemClayPot16, (edu.cmu.tartan.item.Item) itemMicrowave26);
        edu.cmu.tartan.action.Action action28 = playerInterpreter3.interpretString("", actionExecutionUnit27);
        edu.cmu.tartan.item.Item item29 = actionExecutionUnit27.directObject();
        edu.cmu.tartan.item.Item item30 = actionExecutionUnit27.indirectObject();
        try {
            playerExecutionEngine1.executeAction(action2, actionExecutionUnit27);
            fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
        }
        assertTrue("'" + action2 + "' != '" + edu.cmu.tartan.action.Action.ACTION_GO_NORTHEAST + "'", action2.equals(edu.cmu.tartan.action.Action.ACTION_GO_NORTHEAST));
        assertTrue("'" + action6 + "' != '" + edu.cmu.tartan.action.Action.ACTION_ERROR + "'", action6.equals(edu.cmu.tartan.action.Action.ACTION_ERROR));
        assertNotNull(strArray14);
        assertTrue("'" + action23 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action23.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray24);
        assertTrue("'" + action28 + "' != '" + edu.cmu.tartan.action.Action.ACTION_PASS + "'", action28.equals(edu.cmu.tartan.action.Action.ACTION_PASS));
        assertNotNull(item29);
        assertNotNull(item30);
    }*/

    @Test
    public void test63() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test63");
        java.lang.String[] strArray2 = new java.lang.String[] { "", "" };
        java.util.ArrayList<java.lang.String> strList3 = new java.util.ArrayList<java.lang.String>();
        boolean boolean4 = java.util.Collections.addAll((java.util.Collection<java.lang.String>) strList3, strArray2);
        edu.cmu.tartan.goal.GameExploreGoal gameExploreGoal5 = new edu.cmu.tartan.goal.GameExploreGoal((java.util.List<java.lang.String>) strList3);
        edu.cmu.tartan.room.RoomObscured roomObscured8 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        edu.cmu.tartan.action.Action action9 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        edu.cmu.tartan.room.RoomObscured roomObscured12 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        roomObscured8.setOneWayAdjacentRoom(action9, (edu.cmu.tartan.room.Room) roomObscured12);
        edu.cmu.tartan.Player player14 = new edu.cmu.tartan.Player((edu.cmu.tartan.room.Room) roomObscured12);
        gameExploreGoal5.setPlayer(player14);
        player14.addPossiblePoints((int) (byte) 100);
        assertNotNull(strArray2);
        assertTrue("'" + boolean4 + "' != '" + true + "'", boolean4 == true);
        assertTrue("'" + action9 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action9.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
    }

    @Test
    public void test64() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test64");
        java.lang.String[] strArray6 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick7 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray6);
        edu.cmu.tartan.item.ItemClayPot itemClayPot8 = new edu.cmu.tartan.item.ItemClayPot("hi!", "", strArray6);
        itemClayPot8.destroy();
        edu.cmu.tartan.room.Room room10 = itemClayPot8.relatedRoom();
        java.lang.Boolean boolean11 = itemClayPot8.inspect();
        assertNotNull(strArray6);
        assertNull(room10);
        assertTrue("'" + boolean11 + "' != '" + true + "'", boolean11.equals(true));
    }

    @Test
    public void test65() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test65");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_LOOK;
        assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_LOOK + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_LOOK));
    }

    @Test
    public void test66() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test66");
        java.lang.String[] strArray9 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick10 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray9);
        java.lang.String[] strArray11 = itemBrick10.getAliases();
        edu.cmu.tartan.item.ItemVendingMachine itemVendingMachine12 = new edu.cmu.tartan.item.ItemVendingMachine("hi!", "hi!", strArray11);
        java.lang.String[] strArray17 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick18 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray17);
        java.lang.String[] strArray19 = itemBrick18.getAliases();
        itemBrick18.setVisible(false);
        edu.cmu.tartan.action.ActionExecutionUnit actionExecutionUnit22 = new edu.cmu.tartan.action.ActionExecutionUnit((edu.cmu.tartan.item.Item) itemVendingMachine12, (edu.cmu.tartan.item.Item) itemBrick18);
        edu.cmu.tartan.room.RoomLockable roomLockable23 = new edu.cmu.tartan.room.RoomLockable("I don't see that here.", "hi!", false, (edu.cmu.tartan.item.Item) itemVendingMachine12);
        assertNotNull(strArray9);
        assertNotNull(strArray11);
        assertNotNull(strArray17);
        assertNotNull(strArray19);
    }

    @Test
    public void test67() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test67");
        edu.cmu.tartan.room.RoomExcavatable roomExcavatable3 = new edu.cmu.tartan.room.RoomExcavatable("hi!", "hi!", "hi!");
        java.lang.Class<?> wildcardClass4 = roomExcavatable3.getClass();
        assertNotNull(wildcardClass4);
    }

    @Test
    public void test68() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test68");
        java.lang.String[] strArray2 = new java.lang.String[] { "", "" };
        java.util.ArrayList<java.lang.String> strList3 = new java.util.ArrayList<java.lang.String>();
        boolean boolean4 = java.util.Collections.addAll((java.util.Collection<java.lang.String>) strList3, strArray2);
        edu.cmu.tartan.goal.GameExploreGoal gameExploreGoal5 = new edu.cmu.tartan.goal.GameExploreGoal((java.util.List<java.lang.String>) strList3);
        edu.cmu.tartan.room.RoomObscured roomObscured8 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        edu.cmu.tartan.action.Action action9 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        edu.cmu.tartan.room.RoomObscured roomObscured12 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        roomObscured8.setOneWayAdjacentRoom(action9, (edu.cmu.tartan.room.Room) roomObscured12);
        edu.cmu.tartan.Player player14 = new edu.cmu.tartan.Player((edu.cmu.tartan.room.Room) roomObscured12);
        gameExploreGoal5.setPlayer(player14);
        edu.cmu.tartan.room.RoomObscured roomObscured18 = new edu.cmu.tartan.room.RoomObscured("hi!", "");
        edu.cmu.tartan.room.RoomObscured roomObscured22 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        edu.cmu.tartan.action.Action action23 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        edu.cmu.tartan.room.RoomObscured roomObscured26 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        roomObscured22.setOneWayAdjacentRoom(action23, (edu.cmu.tartan.room.Room) roomObscured26);
        roomObscured18.setAdjacentRoomTransitionMessageWithDelay("hi!", action23, (int) '4');
        edu.cmu.tartan.action.Action action32 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray33 = action32.getAliases();
        edu.cmu.tartan.item.ItemFood itemFood34 = new edu.cmu.tartan.item.ItemFood("hi!", "", strArray33);
        java.lang.String str35 = itemFood34.description();
        edu.cmu.tartan.action.Action action40 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray41 = action40.getAliases();
        edu.cmu.tartan.item.ItemFood itemFood42 = new edu.cmu.tartan.item.ItemFood("hi!", "", strArray41);
        edu.cmu.tartan.item.ItemMicrowave itemMicrowave43 = new edu.cmu.tartan.item.ItemMicrowave("hi!", "", strArray41);
        edu.cmu.tartan.item.Item[] itemArray44 = new edu.cmu.tartan.item.Item[] { itemFood34, itemMicrowave43 };
        java.util.ArrayList<edu.cmu.tartan.item.Item> itemList45 = new java.util.ArrayList<edu.cmu.tartan.item.Item>();
        boolean boolean46 = java.util.Collections.addAll((java.util.Collection<edu.cmu.tartan.item.Item>) itemList45, itemArray44);
        edu.cmu.tartan.Player player47 = new edu.cmu.tartan.Player((edu.cmu.tartan.room.Room) roomObscured18, (java.util.List<edu.cmu.tartan.item.Item>) itemList45);
        java.lang.String[] strArray54 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick55 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray54);
        java.lang.String[] strArray56 = itemBrick55.getAliases();
        edu.cmu.tartan.item.ItemVendingMachine itemVendingMachine57 = new edu.cmu.tartan.item.ItemVendingMachine("hi!", "hi!", strArray56);
        java.lang.String[] strArray62 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick63 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray62);
        java.lang.String[] strArray64 = itemBrick63.getAliases();
        itemBrick63.setVisible(false);
        edu.cmu.tartan.action.ActionExecutionUnit actionExecutionUnit67 = new edu.cmu.tartan.action.ActionExecutionUnit((edu.cmu.tartan.item.Item) itemVendingMachine57, (edu.cmu.tartan.item.Item) itemBrick63);
        player47.score((edu.cmu.tartan.properties.Valuable) itemVendingMachine57);
        boolean boolean69 = player14.pickup((edu.cmu.tartan.item.Item) itemVendingMachine57);
        edu.cmu.tartan.room.Room room70 = player14.currentRoom();
        player14.lookAround();
        assertNotNull(strArray2);
        assertTrue("'" + boolean4 + "' != '" + true + "'", boolean4 == true);
        assertTrue("'" + action9 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action9.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertTrue("'" + action23 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action23.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertTrue("'" + action32 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action32.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray33);
        assertTrue("'" + str35 + "' != '" + "hi!" + "'", str35.equals("hi!"));
        assertTrue("'" + action40 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action40.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray41);
        assertNotNull(itemArray44);
        assertTrue("'" + boolean46 + "' != '" + true + "'", boolean46 == true);
        assertNotNull(strArray54);
        assertNotNull(strArray56);
        assertNotNull(strArray62);
        assertNotNull(strArray64);
        assertTrue("'" + boolean69 + "' != '" + true + "'", boolean69 == true);
        assertNotNull(room70);
    }

    @Test
    public void test69() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test69");
        java.net.Socket socket0 = null;
        edu.cmu.tartan.manager.IQueueHandler iQueueHandler1 = null;
        edu.cmu.tartan.socket.DesignerClientThread designerClientThread2 = new edu.cmu.tartan.socket.DesignerClientThread(socket0, iQueueHandler1);
        designerClientThread2.setIsLogin(false);
        try {
            designerClientThread2.run();
            fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
        }
    }

    @Test
    public void test70() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test70");
        edu.cmu.tartan.room.Room room0 = new edu.cmu.tartan.room.Room();
        edu.cmu.tartan.room.RoomObscured roomObscured3 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        edu.cmu.tartan.action.Action action4 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        edu.cmu.tartan.room.RoomObscured roomObscured7 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        roomObscured3.setOneWayAdjacentRoom(action4, (edu.cmu.tartan.room.Room) roomObscured7);
        edu.cmu.tartan.Player player9 = new edu.cmu.tartan.Player((edu.cmu.tartan.room.Room) roomObscured7);
        java.lang.String str10 = roomObscured7.description();
        edu.cmu.tartan.action.Action action11 = room0.getDirectionForRoom((edu.cmu.tartan.room.Room) roomObscured7);
        assertTrue("'" + action4 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action4.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertTrue("'" + str10 + "' != '" + "hi!" + "'", str10.equals("hi!"));
        assertTrue("'" + action11 + "' != '" + edu.cmu.tartan.action.Action.ACTION_UNKNOWN + "'", action11.equals(edu.cmu.tartan.action.Action.ACTION_UNKNOWN));
    }

    @Test
    public void test71() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test71");
        edu.cmu.tartan.room.RoomObscured roomObscured2 = new edu.cmu.tartan.room.RoomObscured("hi!", "");
        edu.cmu.tartan.room.RoomObscured roomObscured5 = new edu.cmu.tartan.room.RoomObscured("", "");
        roomObscured5.setObscured(true);
        boolean boolean8 = roomObscured2.isAdjacentToRoom((edu.cmu.tartan.room.Room) roomObscured5);
        edu.cmu.tartan.action.Action action9 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        edu.cmu.tartan.room.RoomObscured roomObscured12 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        edu.cmu.tartan.action.Action action13 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        edu.cmu.tartan.room.RoomObscured roomObscured16 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        roomObscured12.setOneWayAdjacentRoom(action13, (edu.cmu.tartan.room.Room) roomObscured16);
        edu.cmu.tartan.action.Action action18 = edu.cmu.tartan.action.Action.ACTION_DROP;
        boolean boolean19 = roomObscured16.canMoveToRoomInDirection(action18);
        roomObscured5.setOneWayAdjacentRoom(action9, (edu.cmu.tartan.room.Room) roomObscured16);
        assertTrue("'" + boolean8 + "' != '" + false + "'", boolean8 == false);
        assertTrue("'" + action9 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action9.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertTrue("'" + action13 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action13.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertTrue("'" + action18 + "' != '" + edu.cmu.tartan.action.Action.ACTION_DROP + "'", action18.equals(edu.cmu.tartan.action.Action.ACTION_DROP));
        assertTrue("'" + boolean19 + "' != '" + false + "'", boolean19 == false);
    }

    @Test
    public void test72() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test72");
        edu.cmu.tartan.xml.XmlResponseAddUser xmlResponseAddUser0 = new edu.cmu.tartan.xml.XmlResponseAddUser();
        xmlResponseAddUser0.makeResponseXmlString();
        xmlResponseAddUser0.makeResponseXmlString();
    }

    @Test
    public void test73() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test73");
        edu.cmu.tartan.action.Action action6 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray7 = action6.getAliases();
        edu.cmu.tartan.item.ItemFood itemFood8 = new edu.cmu.tartan.item.ItemFood("hi!", "", strArray7);
        edu.cmu.tartan.item.ItemDeskLight itemDeskLight9 = new edu.cmu.tartan.item.ItemDeskLight("hi!", "hi!", strArray7);
        edu.cmu.tartan.item.ItemLadder itemLadder10 = new edu.cmu.tartan.item.ItemLadder("I don't see that here.", "", strArray7);
        assertTrue("'" + action6 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action6.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray7);
    }

    @Test
    public void test74() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test74");
        edu.cmu.tartan.action.Action action0 = edu.cmu.tartan.action.Action.ACTION_UNKNOWN;
        assertTrue("'" + action0 + "' != '" + edu.cmu.tartan.action.Action.ACTION_UNKNOWN + "'", action0.equals(edu.cmu.tartan.action.Action.ACTION_UNKNOWN));
    }

    @Test
    public void test75() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test75");
        java.lang.String[] strArray6 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick7 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray6);
        java.lang.String[] strArray8 = itemBrick7.getAliases();
        edu.cmu.tartan.item.ItemFolder itemFolder9 = new edu.cmu.tartan.item.ItemFolder("hi!", "", strArray8);
        java.lang.Boolean boolean10 = itemFolder9.open();
        itemFolder9.setOpenMessage("hi!");
        assertNotNull(strArray6);
        assertNotNull(strArray8);
        assertTrue("'" + boolean10 + "' != '" + true + "'", boolean10.equals(true));
    }

    @Test
    public void test76() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test76");
        java.lang.String[] strArray10 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick11 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray10);
        java.lang.String[] strArray12 = itemBrick11.getAliases();
        edu.cmu.tartan.item.ItemVendingMachine itemVendingMachine13 = new edu.cmu.tartan.item.ItemVendingMachine("hi!", "hi!", strArray12);
        edu.cmu.tartan.item.ItemShovel itemShovel14 = new edu.cmu.tartan.item.ItemShovel("hi!", "", strArray12);
        edu.cmu.tartan.item.ItemMagicBox itemMagicBox15 = new edu.cmu.tartan.item.ItemMagicBox("", "hi!", strArray12);
        edu.cmu.tartan.action.Action action18 = edu.cmu.tartan.action.Action.ACTION_TAKE;
        java.lang.String[] strArray19 = action18.getAliases();
        edu.cmu.tartan.item.ItemGold itemGold20 = new edu.cmu.tartan.item.ItemGold("", "", strArray19);
        itemMagicBox15.install((edu.cmu.tartan.item.Item) itemGold20);
        assertNotNull(strArray10);
        assertNotNull(strArray12);
        assertTrue("'" + action18 + "' != '" + edu.cmu.tartan.action.Action.ACTION_TAKE + "'", action18.equals(edu.cmu.tartan.action.Action.ACTION_TAKE));
        assertNotNull(strArray19);
    }

    @Test
    public void test77() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test77");
        java.lang.String[] strArray3 = new java.lang.String[] { "", "" };
        java.util.ArrayList<java.lang.String> strList4 = new java.util.ArrayList<java.lang.String>();
        boolean boolean5 = java.util.Collections.addAll((java.util.Collection<java.lang.String>) strList4, strArray3);
        edu.cmu.tartan.goal.GameExploreGoal gameExploreGoal6 = new edu.cmu.tartan.goal.GameExploreGoal((java.util.List<java.lang.String>) strList4);
        edu.cmu.tartan.room.RoomObscured roomObscured9 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        edu.cmu.tartan.action.Action action10 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        edu.cmu.tartan.room.RoomObscured roomObscured13 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        roomObscured9.setOneWayAdjacentRoom(action10, (edu.cmu.tartan.room.Room) roomObscured13);
        edu.cmu.tartan.Player player15 = new edu.cmu.tartan.Player((edu.cmu.tartan.room.Room) roomObscured13);
        gameExploreGoal6.setPlayer(player15);
        edu.cmu.tartan.room.RoomObscured roomObscured19 = new edu.cmu.tartan.room.RoomObscured("hi!", "");
        edu.cmu.tartan.room.RoomObscured roomObscured23 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        edu.cmu.tartan.action.Action action24 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        edu.cmu.tartan.room.RoomObscured roomObscured27 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        roomObscured23.setOneWayAdjacentRoom(action24, (edu.cmu.tartan.room.Room) roomObscured27);
        roomObscured19.setAdjacentRoomTransitionMessageWithDelay("hi!", action24, (int) '4');
        edu.cmu.tartan.action.Action action33 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray34 = action33.getAliases();
        edu.cmu.tartan.item.ItemFood itemFood35 = new edu.cmu.tartan.item.ItemFood("hi!", "", strArray34);
        java.lang.String str36 = itemFood35.description();
        edu.cmu.tartan.action.Action action41 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray42 = action41.getAliases();
        edu.cmu.tartan.item.ItemFood itemFood43 = new edu.cmu.tartan.item.ItemFood("hi!", "", strArray42);
        edu.cmu.tartan.item.ItemMicrowave itemMicrowave44 = new edu.cmu.tartan.item.ItemMicrowave("hi!", "", strArray42);
        edu.cmu.tartan.item.Item[] itemArray45 = new edu.cmu.tartan.item.Item[] { itemFood35, itemMicrowave44 };
        java.util.ArrayList<edu.cmu.tartan.item.Item> itemList46 = new java.util.ArrayList<edu.cmu.tartan.item.Item>();
        boolean boolean47 = java.util.Collections.addAll((java.util.Collection<edu.cmu.tartan.item.Item>) itemList46, itemArray45);
        edu.cmu.tartan.Player player48 = new edu.cmu.tartan.Player((edu.cmu.tartan.room.Room) roomObscured19, (java.util.List<edu.cmu.tartan.item.Item>) itemList46);
        java.lang.String[] strArray55 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick56 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray55);
        java.lang.String[] strArray57 = itemBrick56.getAliases();
        edu.cmu.tartan.item.ItemVendingMachine itemVendingMachine58 = new edu.cmu.tartan.item.ItemVendingMachine("hi!", "hi!", strArray57);
        java.lang.String[] strArray63 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick64 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray63);
        java.lang.String[] strArray65 = itemBrick64.getAliases();
        itemBrick64.setVisible(false);
        edu.cmu.tartan.action.ActionExecutionUnit actionExecutionUnit68 = new edu.cmu.tartan.action.ActionExecutionUnit((edu.cmu.tartan.item.Item) itemVendingMachine58, (edu.cmu.tartan.item.Item) itemBrick64);
        player48.score((edu.cmu.tartan.properties.Valuable) itemVendingMachine58);
        boolean boolean70 = player15.pickup((edu.cmu.tartan.item.Item) itemVendingMachine58);
        edu.cmu.tartan.goal.GamePointsGoal gamePointsGoal71 = new edu.cmu.tartan.goal.GamePointsGoal((java.lang.Integer) 0, player15);
        assertNotNull(strArray3);
        assertTrue("'" + boolean5 + "' != '" + true + "'", boolean5 == true);
        assertTrue("'" + action10 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action10.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertTrue("'" + action24 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action24.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertTrue("'" + action33 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action33.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray34);
        assertTrue("'" + str36 + "' != '" + "hi!" + "'", str36.equals("hi!"));
        assertTrue("'" + action41 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action41.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray42);
        assertNotNull(itemArray45);
        assertTrue("'" + boolean47 + "' != '" + true + "'", boolean47 == true);
        assertNotNull(strArray55);
        assertNotNull(strArray57);
        assertNotNull(strArray63);
        assertNotNull(strArray65);
        assertTrue("'" + boolean70 + "' != '" + true + "'", boolean70 == true);
    }

    @Test
    public void test78() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test78");
        java.lang.String[] strArray6 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick7 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray6);
        edu.cmu.tartan.item.ItemClayPot itemClayPot8 = new edu.cmu.tartan.item.ItemClayPot("hi!", "", strArray6);
        itemClayPot8.setDestroyMessage("hi!");
        edu.cmu.tartan.action.Action action15 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray16 = action15.getAliases();
        edu.cmu.tartan.item.ItemFood itemFood17 = new edu.cmu.tartan.item.ItemFood("hi!", "", strArray16);
        edu.cmu.tartan.item.ItemMicrowave itemMicrowave18 = new edu.cmu.tartan.item.ItemMicrowave("hi!", "", strArray16);
        edu.cmu.tartan.action.ActionExecutionUnit actionExecutionUnit19 = new edu.cmu.tartan.action.ActionExecutionUnit((edu.cmu.tartan.item.Item) itemClayPot8, (edu.cmu.tartan.item.Item) itemMicrowave18);
        edu.cmu.tartan.action.Action action22 = edu.cmu.tartan.action.Action.ACTION_TAKE;
        java.lang.String[] strArray23 = action22.getAliases();
        edu.cmu.tartan.item.ItemTorch itemTorch24 = new edu.cmu.tartan.item.ItemTorch("", "", strArray23);
        itemTorch24.setInspectMessage("hi!");
        itemMicrowave18.install((edu.cmu.tartan.item.Item) itemTorch24);
        assertNotNull(strArray6);
        assertTrue("'" + action15 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action15.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray16);
        assertTrue("'" + action22 + "' != '" + edu.cmu.tartan.action.Action.ACTION_TAKE + "'", action22.equals(edu.cmu.tartan.action.Action.ACTION_TAKE));
        assertNotNull(strArray23);
    }

    @Test
    public void test79() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test79");
        edu.cmu.tartan.room.RoomObscured roomObscured2 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        edu.cmu.tartan.action.Action action3 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        edu.cmu.tartan.room.RoomObscured roomObscured6 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        roomObscured2.setOneWayAdjacentRoom(action3, (edu.cmu.tartan.room.Room) roomObscured6);
        edu.cmu.tartan.Player player8 = new edu.cmu.tartan.Player((edu.cmu.tartan.room.Room) roomObscured6);
        edu.cmu.tartan.action.Action action9 = edu.cmu.tartan.action.Action.ACTION_DROP;
        edu.cmu.tartan.room.RoomObscured roomObscured12 = new edu.cmu.tartan.room.RoomObscured("hi!", "");
        edu.cmu.tartan.room.RoomObscured roomObscured16 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        edu.cmu.tartan.action.Action action17 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        edu.cmu.tartan.room.RoomObscured roomObscured20 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        roomObscured16.setOneWayAdjacentRoom(action17, (edu.cmu.tartan.room.Room) roomObscured20);
        roomObscured12.setAdjacentRoomTransitionMessageWithDelay("hi!", action17, (int) '4');
        edu.cmu.tartan.action.Action action26 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray27 = action26.getAliases();
        edu.cmu.tartan.item.ItemFood itemFood28 = new edu.cmu.tartan.item.ItemFood("hi!", "", strArray27);
        java.lang.String str29 = itemFood28.description();
        edu.cmu.tartan.action.Action action34 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray35 = action34.getAliases();
        edu.cmu.tartan.item.ItemFood itemFood36 = new edu.cmu.tartan.item.ItemFood("hi!", "", strArray35);
        edu.cmu.tartan.item.ItemMicrowave itemMicrowave37 = new edu.cmu.tartan.item.ItemMicrowave("hi!", "", strArray35);
        edu.cmu.tartan.item.Item[] itemArray38 = new edu.cmu.tartan.item.Item[] { itemFood28, itemMicrowave37 };
        java.util.ArrayList<edu.cmu.tartan.item.Item> itemList39 = new java.util.ArrayList<edu.cmu.tartan.item.Item>();
        boolean boolean40 = java.util.Collections.addAll((java.util.Collection<edu.cmu.tartan.item.Item>) itemList39, itemArray38);
        edu.cmu.tartan.Player player41 = new edu.cmu.tartan.Player((edu.cmu.tartan.room.Room) roomObscured12, (java.util.List<edu.cmu.tartan.item.Item>) itemList39);
        java.util.List<edu.cmu.tartan.item.Item> itemList42 = null;
        edu.cmu.tartan.Player player43 = new edu.cmu.tartan.Player((edu.cmu.tartan.room.Room) roomObscured12, itemList42);
        roomObscured6.setOneWayAdjacentRoom(action9, (edu.cmu.tartan.room.Room) roomObscured12);
        assertTrue("'" + action3 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action3.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertTrue("'" + action9 + "' != '" + edu.cmu.tartan.action.Action.ACTION_DROP + "'", action9.equals(edu.cmu.tartan.action.Action.ACTION_DROP));
        assertTrue("'" + action17 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action17.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertTrue("'" + action26 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action26.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray27);
        assertTrue("'" + str29 + "' != '" + "hi!" + "'", str29.equals("hi!"));
        assertTrue("'" + action34 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action34.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray35);
        assertNotNull(itemArray38);
        assertTrue("'" + boolean40 + "' != '" + true + "'", boolean40 == true);
    }

    @Test
    public void test80() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test80");
        java.lang.String[] strArray8 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick9 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray8);
        edu.cmu.tartan.item.ItemClayPot itemClayPot10 = new edu.cmu.tartan.item.ItemClayPot("hi!", "", strArray8);
        edu.cmu.tartan.item.ItemKeycardReader itemKeycardReader11 = new edu.cmu.tartan.item.ItemKeycardReader("Demo goal", "", strArray8);
        assertNotNull(strArray8);
    }

    @Test
    public void test81() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test81");
        edu.cmu.tartan.xml.XmlWriter.saveXmlStringToFile("", "Demo goal");
    }

    @Test
    public void test82() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test82");
        edu.cmu.tartan.games.ObscuredRoomGame obscuredRoomGame0 = new edu.cmu.tartan.games.ObscuredRoomGame();
        java.lang.String str1 = obscuredRoomGame0.getName();
        assertTrue("'" + str1 + "' != '" + "Obscured" + "'", str1.equals("Obscured"));
    }

    @Test
    public void test83() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test83");
        edu.cmu.tartan.action.Action action4 = edu.cmu.tartan.action.Action.ACTION_TAKE;
        java.lang.String[] strArray5 = action4.getAliases();
        edu.cmu.tartan.item.ItemDynamite itemDynamite6 = new edu.cmu.tartan.item.ItemDynamite("", "", strArray5);
        edu.cmu.tartan.item.ItemComputer itemComputer7 = new edu.cmu.tartan.item.ItemComputer("hi!", "", strArray5);
        java.lang.Boolean boolean8 = itemComputer7.inspect();
        assertTrue("'" + action4 + "' != '" + edu.cmu.tartan.action.Action.ACTION_TAKE + "'", action4.equals(edu.cmu.tartan.action.Action.ACTION_TAKE));
        assertNotNull(strArray5);
        assertTrue("'" + boolean8 + "' != '" + true + "'", boolean8.equals(true));
    }

    @Test
    public void test84() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test84");
        java.lang.String[] strArray6 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick7 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray6);
        edu.cmu.tartan.item.ItemClayPot itemClayPot8 = new edu.cmu.tartan.item.ItemClayPot("hi!", "", strArray6);
        itemClayPot8.setDestroyMessage("hi!");
        edu.cmu.tartan.action.Action action15 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray16 = action15.getAliases();
        edu.cmu.tartan.item.ItemFood itemFood17 = new edu.cmu.tartan.item.ItemFood("hi!", "", strArray16);
        edu.cmu.tartan.item.ItemMicrowave itemMicrowave18 = new edu.cmu.tartan.item.ItemMicrowave("hi!", "", strArray16);
        edu.cmu.tartan.action.ActionExecutionUnit actionExecutionUnit19 = new edu.cmu.tartan.action.ActionExecutionUnit((edu.cmu.tartan.item.Item) itemClayPot8, (edu.cmu.tartan.item.Item) itemMicrowave18);
        edu.cmu.tartan.room.RoomObscured roomObscured22 = new edu.cmu.tartan.room.RoomObscured("hi!", "");
        edu.cmu.tartan.room.RoomObscured roomObscured26 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        edu.cmu.tartan.action.Action action27 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        edu.cmu.tartan.room.RoomObscured roomObscured30 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        roomObscured26.setOneWayAdjacentRoom(action27, (edu.cmu.tartan.room.Room) roomObscured30);
        roomObscured22.setAdjacentRoomTransitionMessageWithDelay("hi!", action27, (int) '4');
        itemClayPot8.setRelatedRoom((edu.cmu.tartan.room.Room) roomObscured22);
        java.lang.String str35 = roomObscured22.visibleItems();
        edu.cmu.tartan.Player player36 = new edu.cmu.tartan.Player((edu.cmu.tartan.room.Room) roomObscured22);
        assertNotNull(strArray6);
        assertTrue("'" + action15 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action15.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray16);
        assertTrue("'" + action27 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action27.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertTrue("'" + str35 + "' != '" + "" + "'", str35.equals(""));
    }

    @Test
    public void test85() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test85");
        java.lang.String[] strArray6 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick7 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray6);
        edu.cmu.tartan.item.ItemClayPot itemClayPot8 = new edu.cmu.tartan.item.ItemClayPot("hi!", "", strArray6);
        itemClayPot8.setDestroyMessage("hi!");
        edu.cmu.tartan.action.Action action15 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        java.lang.String[] strArray16 = action15.getAliases();
        edu.cmu.tartan.item.ItemFood itemFood17 = new edu.cmu.tartan.item.ItemFood("hi!", "", strArray16);
        edu.cmu.tartan.item.ItemMicrowave itemMicrowave18 = new edu.cmu.tartan.item.ItemMicrowave("hi!", "", strArray16);
        edu.cmu.tartan.action.ActionExecutionUnit actionExecutionUnit19 = new edu.cmu.tartan.action.ActionExecutionUnit((edu.cmu.tartan.item.Item) itemClayPot8, (edu.cmu.tartan.item.Item) itemMicrowave18);
        java.lang.String[] strArray26 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick27 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray26);
        edu.cmu.tartan.item.ItemSafe itemSafe28 = new edu.cmu.tartan.item.ItemSafe("hi!", "hi!", strArray26);
        java.lang.String[] strArray35 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick36 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray35);
        edu.cmu.tartan.item.ItemClayPot itemClayPot37 = new edu.cmu.tartan.item.ItemClayPot("hi!", "", strArray35);
        itemClayPot37.destroy();
        java.lang.String[] strArray41 = new java.lang.String[] { "", "" };
        java.util.ArrayList<java.lang.String> strList42 = new java.util.ArrayList<java.lang.String>();
        boolean boolean43 = java.util.Collections.addAll((java.util.Collection<java.lang.String>) strList42, strArray41);
        edu.cmu.tartan.goal.GameExploreGoal gameExploreGoal44 = new edu.cmu.tartan.goal.GameExploreGoal((java.util.List<java.lang.String>) strList42);
        edu.cmu.tartan.room.RoomObscured roomObscured47 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        edu.cmu.tartan.action.Action action48 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        edu.cmu.tartan.room.RoomObscured roomObscured51 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        roomObscured47.setOneWayAdjacentRoom(action48, (edu.cmu.tartan.room.Room) roomObscured51);
        edu.cmu.tartan.Player player53 = new edu.cmu.tartan.Player((edu.cmu.tartan.room.Room) roomObscured51);
        gameExploreGoal44.setPlayer(player53);
        boolean boolean55 = itemClayPot37.equals((java.lang.Object) gameExploreGoal44);
        itemSafe28.install((edu.cmu.tartan.item.Item) itemClayPot37);
        edu.cmu.tartan.action.ActionExecutionUnit actionExecutionUnit57 = new edu.cmu.tartan.action.ActionExecutionUnit((edu.cmu.tartan.item.Item) itemMicrowave18, (edu.cmu.tartan.item.Item) itemClayPot37);
        assertNotNull(strArray6);
        assertTrue("'" + action15 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action15.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertNotNull(strArray16);
        assertNotNull(strArray26);
        assertNotNull(strArray35);
        assertNotNull(strArray41);
        assertTrue("'" + boolean43 + "' != '" + true + "'", boolean43 == true);
        assertTrue("'" + action48 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action48.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertTrue("'" + boolean55 + "' != '" + false + "'", boolean55 == false);
    }

    @Test
    public void test86() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test86");
        java.lang.String[] strArray8 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick9 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray8);
        edu.cmu.tartan.room.RoomRequiredItem roomRequiredItem10 = new edu.cmu.tartan.room.RoomRequiredItem("Demo goal", "I don't see that here.", "I don't see that here.", "hi!", (edu.cmu.tartan.item.Item) itemBrick9);
        edu.cmu.tartan.item.Item item11 = roomRequiredItem10.requiredItem();
        assertNotNull(strArray8);
        assertNotNull(item11);
    }

    @Test
    public void test87() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test87");
        java.lang.String[] strArray1 = new java.lang.String[] { "I don't see that here." };
        java.util.ArrayList<java.lang.String> strList2 = new java.util.ArrayList<java.lang.String>();
        boolean boolean3 = java.util.Collections.addAll((java.util.Collection<java.lang.String>) strList2, strArray1);
        java.lang.String[] strArray6 = new java.lang.String[] { "", "" };
        java.util.ArrayList<java.lang.String> strList7 = new java.util.ArrayList<java.lang.String>();
        boolean boolean8 = java.util.Collections.addAll((java.util.Collection<java.lang.String>) strList7, strArray6);
        edu.cmu.tartan.goal.GameExploreGoal gameExploreGoal9 = new edu.cmu.tartan.goal.GameExploreGoal((java.util.List<java.lang.String>) strList7);
        edu.cmu.tartan.room.RoomObscured roomObscured12 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        edu.cmu.tartan.action.Action action13 = edu.cmu.tartan.action.Action.ACTION_EXPLODE;
        edu.cmu.tartan.room.RoomObscured roomObscured16 = new edu.cmu.tartan.room.RoomObscured("hi!", "hi!");
        roomObscured12.setOneWayAdjacentRoom(action13, (edu.cmu.tartan.room.Room) roomObscured16);
        edu.cmu.tartan.Player player18 = new edu.cmu.tartan.Player((edu.cmu.tartan.room.Room) roomObscured16);
        gameExploreGoal9.setPlayer(player18);
        edu.cmu.tartan.goal.GameCollectGoal gameCollectGoal20 = new edu.cmu.tartan.goal.GameCollectGoal((java.util.List<java.lang.String>) strList2, player18);
        java.lang.Boolean boolean21 = gameCollectGoal20.isAchieved();
        assertNotNull(strArray1);
        assertTrue("'" + boolean3 + "' != '" + true + "'", boolean3 == true);
        assertNotNull(strArray6);
        assertTrue("'" + boolean8 + "' != '" + true + "'", boolean8 == true);
        assertTrue("'" + action13 + "' != '" + edu.cmu.tartan.action.Action.ACTION_EXPLODE + "'", action13.equals(edu.cmu.tartan.action.Action.ACTION_EXPLODE));
        assertTrue("'" + boolean21 + "' != '" + false + "'", boolean21.equals(false));
    }

/*    @Test
    public void test88() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test88");
        java.lang.String[] strArray6 = new java.lang.String[] { "", "hi!" };
        edu.cmu.tartan.item.ItemBrick itemBrick7 = new edu.cmu.tartan.item.ItemBrick("", "hi!", strArray6);
        edu.cmu.tartan.item.ItemSafe itemSafe8 = new edu.cmu.tartan.item.ItemSafe("hi!", "hi!", strArray6);
        try {
            java.lang.Boolean boolean9 = itemSafe8.open();
            fail("Expected exception of type java.util.NoSuchElementException; message: No line found");
        } catch (java.util.NoSuchElementException e) {
        }
        assertNotNull(strArray6);
    }*/

}
