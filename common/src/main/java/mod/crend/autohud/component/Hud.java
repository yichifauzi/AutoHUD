package mod.crend.autohud.component;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

public class Hud {
    private static boolean dynamic = false;
    private static boolean wasPeeking = false;

    private static State state = null;

    public static boolean actDynamic() {
        return dynamic || wasPeeking;
    }

    public static void disableDynamic() {
        dynamic = false;
        Component.revealAll();
        Component.ChatIndicator.hide();
    }

    public static void enableDynamic() {
        dynamic = true;
        Component.hideAll();
    }

    public static void toggleHud() {
        if (dynamic) disableDynamic();
        else enableDynamic();
    }
    public static void peekHud(boolean doPeek) {
        if (doPeek == wasPeeking) return;

        if (dynamic == doPeek) {
            Component.revealAll();
            Component.ChatIndicator.hide();
        } else {
            Component.hideAll();
        }

        wasPeeking = doPeek;
    }

    public static void registerState(ClientPlayerEntity player) {
        state = new State(player);
        wasPeeking = false;
    }
    public static void tickState(ClientPlayerEntity player) {
        state.tick(player);
    }
    public static void unregisterState() {
        state = null;
    }

    public static boolean shouldShowIcon(StatusEffectInstance instance) {
        if (instance.shouldShowIcon()) {
            Component component = Component.get(instance.getEffectType());
            return (!component.fullyHidden());
        }
        return false;
    }

}
