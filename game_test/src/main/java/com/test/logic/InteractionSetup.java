package com.test.logic;

import com.test.objects.*;

public class InteractionSetup {
    public static void registerAll() {
        InteractionRegistry.register("open_door", (actor, target) -> {
            //((Door) target).setOpen(true);                                   // TODO: Add subclasses of GameObject
        });

        InteractionRegistry.register("talk_npc", (actor, target) -> {
            //DialogueSystem.startDialogue((Entity) target);                    // TODO: Add DialogueSystem
        });

        InteractionRegistry.register("explode_bomb", (actor, target) -> {
            System.out.println("BOOM!!!");
            Animation.animate((GameObject) target, "explode");
        });
    }
}
