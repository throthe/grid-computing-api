package util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thomas_r
 */
public enum JavaVersion {

    VERSION_1_5 {
        @Override
        public boolean isChoosable() {
            return false;
        }

        @Override
        public boolean isSupported() {
            return false;
        }

        @Override
        public String toString() {
            return "Version 5";
        }

        @Override
        public int getCompatibilityRating() {
            return 0;
        }
    },
    VERSION_1_6 {
        @Override
        public boolean isChoosable() {
            return true;
        }

        @Override
        public boolean isSupported() {
            return true;
        }

        @Override
        public String toString() {
            return "Version 6";
        }

        @Override
        public int getCompatibilityRating() {
            return 1;
        }
    },
    VERSION_1_7 {
        @Override
        public boolean isChoosable() {
            return true;
        }

        @Override
        public boolean isSupported() {
            return true;
        }

        @Override
        public String toString() {
            return "Version 7";
        }

        @Override
        public int getCompatibilityRating() {
            return 2;
        }
    },
    VERSION_1_8 {
        @Override
        public boolean isChoosable() {
            return true;
        }

        @Override
        public boolean isSupported() {
            return true;
        }

        @Override
        public String toString() {
            return "Version 8";
        }

        @Override
        public int getCompatibilityRating() {
            return 3;
        }
    },
    VERSION_1_9 {
        @Override
        public boolean isChoosable() {
            return true;
        }

        @Override
        public boolean isSupported() {
            return true;
        }

        @Override
        public String toString() {
            return "Version 9";
        }

        @Override
        public int getCompatibilityRating() {
            return 4;
        }
    },
    UNKNOWN {
        @Override
        public boolean isChoosable() {
            return false;
        }

        @Override
        public boolean isSupported() {
            return true;
        }
    },
    UNSUPPORTED {
        @Override
        public boolean isChoosable() {
            return false;
        }

        @Override
        public boolean isSupported() {
            return false;
        }
    };

    abstract public boolean isChoosable();

    abstract public boolean isSupported();

    public int getCompatibilityRating() {
        return -1;
    }
}
