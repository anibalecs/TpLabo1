package app;

public enum TypeUser {
    Patient(1),
    Doctor(2);

    private int type;

    private TypeUser(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static TypeUser fromId(int type){
        for (TypeUser typee : values()) {
            if (typee.getType() == type) {
                return typee;
            }
        }
        return null;
    }
    }
