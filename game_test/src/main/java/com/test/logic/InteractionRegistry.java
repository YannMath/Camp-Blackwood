package com.test.logic;

import java.util.Map;
import java.util.HashMap;
import com.test.objects.GameObject;

public class InteractionRegistry {
    private static final Map<String, Interaction> interactions = new HashMap<>();

    private InteractionRegistry() {}

    public static void register(String name, Interaction interaction) {
        interactions.put(name, interaction);
    }

    public static void trigger(String name, GameObject actor, GameObject target) {
        Interaction interaction = interactions.get(name);
        if (interaction == null) {
            throw new IllegalStateException("Unknown interaction: " + name);
        }
        interaction.execute(actor, target);
    }
}
