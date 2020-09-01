package cc.fatenetwork.kvoucher.vouch;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Vouch {
    private String name, material, command, displayName;
    private List<String> lore = new ArrayList<>();

    public Vouch(String name, String material, String command) {
        this.name = name;
        this.material = material;
        this.command = command;
    }
}
