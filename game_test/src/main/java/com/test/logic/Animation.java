package com.test.logic;

import java.io.IOException;
import java.util.EnumMap;
import java.util.IdentityHashMap;
import java.util.Map;
import com.test.enums.AnimationType;
import com.test.objects.*;

public class Animation {                                        // TODO: Redo the entire animation system (with a register etc.)
    private static final long FRAME_DURATION_MS = 140;
    private static final long MOVEMENT_TIMEOUT_MS = 180;
    private static final Map<AnimationType, Tilemap[]> frames = new EnumMap<>(AnimationType.class);
    private static final Map<Entity, AnimationState> states = new IdentityHashMap<>();

    public static void init() throws IOException {
        frames.put(AnimationType.PLAYER_IDLE, new Tilemap[] {
            ConvertTileMaps.convertFile("tilemaps/player.txt")
        });

        frames.put(AnimationType.PLAYER_WALKING, new Tilemap[] {
            ConvertTileMaps.convertFile("tilemaps/player-walk-1.txt"),
            ConvertTileMaps.convertFile("tilemaps/player-walk-2.txt")
        });
    }

    public static void animateEntity(Entity entity, AnimationType animation) {
        AnimationState state = states.computeIfAbsent(entity, ignored -> new AnimationState());
        long now = System.currentTimeMillis();

        if (state.currentAnimation != animation) {
            state.currentAnimation = animation;
            state.currentFrame = 0;
            state.lastFrameChange = now;
            entity.setEntityState(animation);
            entity.setTilemap(frames.get(animation)[state.currentFrame]);
        }

        if (animation == AnimationType.PLAYER_WALKING) {
            state.lastMovement = now;
        }
    }

    public static void update(Entity entity) {
        long now = System.currentTimeMillis();
        AnimationState state = states.get(entity);
        if (state == null) return;

        if (state.currentAnimation == AnimationType.PLAYER_WALKING &&
            now - state.lastMovement >= MOVEMENT_TIMEOUT_MS) {

            animateEntity(entity, AnimationType.PLAYER_IDLE);
            return;
        }

        Tilemap[] animationFrames = frames.get(state.currentAnimation);

        if (now - state.lastFrameChange >= FRAME_DURATION_MS) {
            state.currentFrame =
                (state.currentFrame + 1) % animationFrames.length;

            state.lastFrameChange = now;
            entity.setTilemap(animationFrames[state.currentFrame]);
        }
    }

    public static void animate(GameObject g, String animation) {
        System.out.println("Wow you weren't even able to implement working animations yet you dumbass");
    }

    private static final class AnimationState {
        private AnimationType currentAnimation;
        private int currentFrame;
        private long lastFrameChange;
        private long lastMovement;
    }
}