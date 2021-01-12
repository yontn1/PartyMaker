package com.example.partymaker;

public class DatabaseSchema {

    public static final class UsersTable {
        public static final String TABLE_NAME = "users";
        public static final class Columns {
            public static final String ID = "id";
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
        }
    }

    public static final class DrinksTable {
        public static final String TABLE_NAME = "drinks";
        public static final class Columns {
            public static final String ID = "id";
            public static final String NAME = "name";
        }
    }

    public static final class InventoryTable {
        public static final String TABLE_NAME = "inventory";
        public static final class Columns {
            public static final String USER_ID = "user_id";
            public static final String DRINK_ID = "drink_id";
            public static final String QUANTITY = "quantity";
        }
    }

    public static final class FriendsTable {
        public static final String TABLE_NAME = "friends";
        public static final class Columns {
            public static final String USER1_ID = "user1_id";
            public static final String USER2_ID = "user2_id";
        }
    }

    public static final class PartiesTable {
        public static final String TABLE_NAME = "parties";
        public static final class Columns {
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String TIME = "time";
        }
    }

    public static final class PartyUsersTable {
        public static final String TABLE_NAME = "party_users";
        public static final class Columns {
            public static final String PARTY_ID = "party_id";
            public static final String USER_ID = "user_id";
            public static final String STATUS = "status";
        }
    }
}
