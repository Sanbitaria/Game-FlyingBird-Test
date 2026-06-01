package com.bird.main;

public class ShieldProp implements PropertyStrategy {
    @Override
    public void PropEffect(Bird bird) {
        bird.getShield();
    }
}
