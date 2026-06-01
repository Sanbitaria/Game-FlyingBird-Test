package com.bird.main;

public class InvincibleProp implements PropertyStrategy {

    @Override
    public void PropEffect(Bird bird) {
        bird.getInvincible();
    }
}
