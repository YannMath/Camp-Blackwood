package com.test.logic;

import com.test.objects.GameObject;

@FunctionalInterface
public interface Interaction {
    void execute(GameObject actor, GameObject target);
}