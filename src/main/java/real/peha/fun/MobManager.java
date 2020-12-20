package real.peha.fun;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MobManager {
    private static boolean isLucky(int chance) {
        int result = new Random().nextInt(101);

        return chance >= result;
    }

    private static void addEnchantment(ItemStack item, List<Map<?, ?>> effects) {
        for (Map<?, ?> effect : effects) {
            String type = effect.get("type").toString();
            Enchantment enchantment = Enchantment.getByName(type);

            if (enchantment == null) {
                MobsPlugin.getInstance().getLogger().warning("Зачарование " + type + " не существует");
            } else {
                int level = Integer.parseInt(effect.get("level").toString());
                int chance = 100;

                Object rawChance = effect.get("chance");

                if (rawChance != null) {
                    chance = Integer.parseInt(rawChance.toString());
                }

                if (isLucky(chance)) {
                    item.addUnsafeEnchantment(enchantment, level);
                }
            }
        }
    }

    public static void setMaxHp(LivingEntity mob, int hp, int chance) {
        if (isLucky(chance)) {
            mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(hp);
            mob.setHealth(hp);
        }
    }

    public static void setMainHand(LivingEntity mob, String material, List<Map<?, ?>> effects, int chance) {
        if (isLucky(chance)) {
            try {
                ItemStack item = new ItemStack(Enum.valueOf(Material.class, material), 1);
                addEnchantment(item, effects);

                mob.getEquipment().setItemInMainHand(item);
            } catch (IllegalArgumentException err) {
                MobsPlugin.getInstance().getLogger().warning("Предмет/блок/интструмент " + material + " не существует");
            }
        }
    }

    public static void setHelmet(LivingEntity mob, String material, List<Map<?, ?>> effects, int chance) {
        if (isLucky(chance)) {
            try {
                ItemStack item = new ItemStack(Enum.valueOf(Material.class, material), 1);
                addEnchantment(item, effects);

                mob.getEquipment().setHelmet(item);
            } catch (IllegalArgumentException err) {
                MobsPlugin.getInstance().getLogger().warning("Предмет/блок/интструмент " + material + " не существует");
            }
        }
    }

    public static void setChestplate(LivingEntity mob, String material, List<Map<?, ?>> effects, int chance) {
        if (isLucky(chance)) {
            try {
                ItemStack item = new ItemStack(Enum.valueOf(Material.class, material), 1);
                addEnchantment(item, effects);

                mob.getEquipment().setChestplate(item);
            } catch (IllegalArgumentException err) {
                MobsPlugin.getInstance().getLogger().warning("Предмет/блок/интструмент " + material + " не существует");
            }
        }
    }

    public static void setLeggings(LivingEntity mob, String material, List<Map<?, ?>> effects, int chance) {
        if (isLucky(chance)) {
            try {
                ItemStack item = new ItemStack(Enum.valueOf(Material.class, material), 1);
                addEnchantment(item, effects);

                mob.getEquipment().setLeggings(item);
            } catch (IllegalArgumentException err) {
                MobsPlugin.getInstance().getLogger().warning("Предмет/блок/интструмент " + material + " не существует");
            }
        }
    }

    public static void setBoots(LivingEntity mob, String material, List<Map<?, ?>> effects, int chance) {
        if (isLucky(chance)) {
            try {
                ItemStack item = new ItemStack(Enum.valueOf(Material.class, material), 1);
                addEnchantment(item, effects);

                mob.getEquipment().setBoots(item);
            } catch (IllegalArgumentException err) {
                MobsPlugin.getInstance().getLogger().warning("Предмет/блок/интструмент " + material + " не существует");
            }
        }
    }

    public static void dropItem(LivingEntity mob, String material, int amount, int chance) {
        if (isLucky(chance)) {
            try {
                Location location = mob.getLocation();
                World world = location.getWorld();

                ItemStack item = new ItemStack(Enum.valueOf(Material.class, material), amount);

                world.dropItem(location, item);
            } catch (IllegalArgumentException err) {
                MobsPlugin.getInstance().getLogger().warning("Предмет/блок/интструмент " + material + " не существует");
            }
        }
    }

    public static void addEffect(LivingEntity mob, String type, int duration, int strength, int chance) {
        if (isLucky(chance)) {
            PotionEffectType pointEffect = PotionEffectType.getByName(type);

            if (pointEffect == null) {
                MobsPlugin.getInstance().getLogger().warning("Эффект " + type + " не существует");
            } else {
                PotionEffect effect = new PotionEffect(pointEffect, duration, strength);

                mob.addPotionEffect(effect);
            }
        }
    }
}
