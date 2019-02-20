package com.huertix.gavines;

public enum LocationsEnum {

        GAVINES_RECIBIDOR("gavines-recibidor"),
        GAVINES_TRASTERO("gavines-trastero"),
        TRESCANTOS_PATIO("trescantos-patio"),
        TRESCANTOS_BUHARDILLA("trescantos-buhardilla");

        private String location;

        LocationsEnum(String location) {
            this.location = location;
        }

        public String location() {
            return location;
        }

}
