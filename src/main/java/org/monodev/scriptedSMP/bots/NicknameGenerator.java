package org.monodev.scriptedSMP.bots;

import org.jspecify.annotations.NonNull;
import org.monodev.scriptedSMP.ScriptedSMP;
import org.monodev.scriptedSMP.api.ScriptedSMPApi;
import org.monodev.scriptedSMP.api.events.nicknames.NicknameGenerateEvent;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class NicknameGenerator {
    private NicknameGenerator() {}

    private static final List<String> FIRST = List.of(
            "Shooting",
            "Capybara",
            "Bliss",
            "Blissful",
            "Shading",
            "Void",
            "Flower",
            "Tulip",
            "Bloom",
            "Destroyer",
            "Feral",
            "Kitty",
            "Rojo",
            "Ender",
            "Fire",
            "Water",
            "Earth"
    );

    private static final List<String> END = List.of(
            "Star",
            "Cat",
            "Bloom",
            "Wormhole",
            "Cosmic",
            "Sky",
            "Poppy",
            "Dragon",
            "Flame",
            "Bender"
    );

    public static @NonNull String genNickname() {
        return genNickname(ThreadLocalRandom.current().nextInt(0, 999999999));
    }

    public static @NonNull String genNickname(int seed) {
        Random random = new Random(seed);

        String finalName = "";

        String first = FIRST.get(random.nextInt(0, FIRST.size()));
        String end = END.get(random.nextInt(0, END.size()));

        boolean addRandomNumbersAtEnd = random.nextInt(5) == 0;

        int numberLength = addRandomNumbersAtEnd ? 2 : 0;
        int remaining = 16 - first.length() - numberLength;

        if (end.length() > remaining) {
            end = end.substring(0, remaining);
        }

        first = randomCase(first, random);
        end = randomCase(end, random);

        boolean addSeparator = random.nextBoolean() && remaining > 0;

        finalName += first;
        if (addSeparator) {
            finalName += "_";
        }
        finalName += end;

        if (addRandomNumbersAtEnd) {
            finalName += String.valueOf(random.nextInt(0, 100));
        }

        ScriptedSMPApi api = ScriptedSMP.instance.getApi();
        NicknameGenerateEvent generateEvent = new NicknameGenerateEvent(api, finalName);
        api.events().publish(generateEvent);

        return generateEvent.getNickname();
    }

    private static String randomCase(String input, @NonNull Random random) {
        return switch (random.nextInt(10)) {
            case 0, 1 -> input.toLowerCase(Locale.ROOT);
            case 2, 3 -> Character.toLowerCase(input.charAt(0))
                    + input.substring(1);
            default -> input;
        };
    }
}
