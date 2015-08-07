package net.chocomelon.sketch;

public class Events {

    public static class ColorChangeEvent {
        int color;

        public ColorChangeEvent(int color) {
            this.color = color;
        }
    }
}
