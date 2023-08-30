

public class petStoreAccess {
    private static PetStore storeInstance = null;

    private petStoreAccess(){
    }

    public static PetStore getStoreInstance(){
        if (storeInstance == null){
            throw new IllegalStateException("Single store is being accessed before initialization");
        }
        return storeInstance;
    }

    public static void initialize(String storeName, int fLabel, int lLabel){
        if (storeInstance != null){
            throw new IllegalStateException("Can't reinitialize the residence");
        }
        storeInstance = new PetStore(storeName,fLabel,lLabel);
    }

    }

