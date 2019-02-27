package com.org.cache.enums;

public enum Direction {

    NORTH {
        @Override
        public Direction getOppositeDirection() {
            return SOUTH;
        }
    },
    SOUTH {
        @Override
        public Direction getOppositeDirection() {
            return NORTH;
        }
    },
    EAST {
        @Override
        public Direction getOppositeDirection() {
            return WEST;
        }
    },
    WEST {
        @Override
        public Direction getOppositeDirection() {
            return EAST;
        }
    };

    public abstract Direction getOppositeDirection();

}
