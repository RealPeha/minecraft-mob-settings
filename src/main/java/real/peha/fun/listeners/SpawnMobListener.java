package real.peha.fun.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.entity.LivingEntity;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import real.peha.fun.Config;
import real.peha.fun.MobsPlugin;
import real.peha.fun.MobManager;

public final class SpawnMobListener implements Listener {
    public SpawnMobListener(MobsPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
	public void onSpawn(CreatureSpawnEvent e) {
		String entityType = e.getEntityType().toString().toLowerCase();

		if (Config.has(entityType)) {
			LivingEntity mob = e.getEntity();

			Set<String> entitySettingsKeys = Config.getSectionKeys(entityType);

			for (String setting : entitySettingsKeys) {
				String property = entityType + "." + setting;
				int chance = 100;

				if (Config.has(property + ".chance")) {
					chance = Config.getInt(property + ".chance");
				}

				switch (setting) {
					case "hp": {
						int hp = Config.getInt(property + ".value");

						MobManager.setMaxHp(mob, hp, chance);

						break;
					}
					case "hand": {
						String itemMaterial = Config.getRandomStringList(property + ".items");
						List<Map<?, ?>> effects = Config.getSection(property + ".effects");

						if (itemMaterial.isEmpty()) {
							itemMaterial = Config.getString(property + ".item");
						}

						MobManager.setMainHand(mob, itemMaterial, effects, chance);

						break;
					}
					case "helmet": {
						String itemMaterial = Config.getRandomStringList(property + ".items");
						List<Map<?, ?>> effects = Config.getSection(property + ".effects");

						if (itemMaterial.isEmpty()) {
							itemMaterial = Config.getString(property + ".item");
						}

						MobManager.setHelmet(mob, itemMaterial, effects, chance);

						break;
					}
					case "chestplate": {
						String itemMaterial = Config.getRandomStringList(property + ".items");
						List<Map<?, ?>> effects = Config.getSection(property + ".effects");

						if (itemMaterial.isEmpty()) {
							itemMaterial = Config.getString(property + ".item");
						}

						MobManager.setChestplate(mob, itemMaterial, effects, chance);

						break;
					}
					case "leggings": {
						String itemMaterial = Config.getRandomStringList(property + ".items");
						List<Map<?, ?>> effects = Config.getSection(property + ".effects");

						if (itemMaterial.isEmpty()) {
							itemMaterial = Config.getString(property + ".item");
						}

						MobManager.setLeggings(mob, itemMaterial, effects, chance);

						break;
					}
					case "boots": {
						String itemMaterial = Config.getRandomStringList(property + ".items");
						List<Map<?, ?>> effects = Config.getSection(property + ".effects");

						if (itemMaterial.isEmpty()) {
							itemMaterial = Config.getString(property + ".item");
						}

						MobManager.setBoots(mob, itemMaterial, effects, chance);

						break;
					}
					case "effects": {
						List<Map<?, ?>> effects = Config.getSection(entityType + ".effects");

						for (Map<?, ?> effect : effects) {
							String type = effect.get("type").toString();
							int duration = Integer.parseInt(effect.get("duration").toString());
							int strength = Integer.parseInt(effect.get("strength").toString());
							int potionChance = 100;
			
							Object rawChance = effect.get("chance");
			
							if (rawChance != null) {
								potionChance = Integer.parseInt(rawChance.toString());
							}
							
							MobManager.addEffect(mob, type, duration, strength, potionChance);
						}

						break;
					}
					case "name": {
						String name = Config.getString(property + ".value");
						boolean isVisible = Config.getBoolean(property + ".visible");

						if (Config.has(property + ".values")) {
							name = Config.getRandomStringList(property + ".values");
						}
						
						mob.setCustomNameVisible(isVisible);
						mob.setCustomName(name);
					}
				}
			}
		}
	}

	@EventHandler()
	public void onDamage(EntityDamageByEntityEvent e) {
		String entityType = e.getDamager().getType().toString().toLowerCase();

		if (Config.has(entityType)) {
			Set<String> entitySettingsKeys = Config.getSectionKeys(entityType);

			for (String setting : entitySettingsKeys) {
				switch (setting) {
					case "damage":
						double damage = Config.getInt(entityType + "." + setting);

						e.setDamage(damage);

						break;
				}
			}
		}
	}

	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		String entityType = e.getEntityType().toString().toLowerCase();

		if (Config.has(entityType)) {
			if (Config.has(entityType + ".drop")) {
				LivingEntity mob = e.getEntity();

				List<Map<?, ?>> drops = Config.getSection(entityType + ".drop");
				boolean clearDefaultDrop = Config.getBoolean(entityType + ".clearDefaultDrop");

				if (clearDefaultDrop) {
					e.getDrops().clear();
				}
			
				for (Map<?, ?> drop : drops) {
					String item = drop.get("item").toString();
					int amount = 1;
					int chance = 100;

					Object rawAmount = drop.get("amount");
					Object rawChance = drop.get("chance");

					if (rawAmount != null) {
						amount = Integer.parseInt(rawAmount.toString());
					}

					if (rawChance != null) {
						chance = Integer.parseInt(rawChance.toString());
					}
					
					MobManager.dropItem(mob, item, amount, chance);
				}
			}

			if (Config.has(entityType + ".xp")) {
				int xp = Config.getInt(entityType + ".xp.value");

				if (Config.has(entityType + ".xp.range")) {
					int from = Config.getInt(entityType + ".xp.range.from");
					int to = Config.getInt(entityType + ".xp.range.to");

					xp = new Random().nextInt(to - from) + from;
				}

				e.setDroppedExp(xp);
			}
		}
	}
}