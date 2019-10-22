package ru.nachos.core.fire;

import ru.nachos.core.Coord;
import ru.nachos.core.controller.ControllerImpl;
import ru.nachos.core.fire.lib.FireFactory;

public final class FireUtils {

    private FireUtils(){}

    private static final FireFactory factory = ControllerImpl.getInstance().getFire().getFactory();

    public static Fire createFire(){
        return new Fire(new FireFactoryImpl());
    }

    public static FireFactory getFactory(){
        return factory;
    }

    public static final class FireBuilder{

        private Fire fire;

        public FireBuilder(Coord center, int perimeter, int accuracy){
            this.fire.setCenter(center);
            this.fire.setPerimeter(perimeter);
            this.fire.setAccuracy(accuracy);
        }

        public FireBuilder setName(String name){
            this.fire.setName(name);
            return this;
        }

        public FireBuilder setFireSpeed(double speed){
            this.fire.setFireSpeed(speed);
            return this;
        }

        public Fire build(){
            return this.fire;
        }
    }
}
