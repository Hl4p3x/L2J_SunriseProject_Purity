/*
 * Copyright (C) 2004-2015 L2J Server
 * 
 * This file is part of L2J Server.
 * 
 * L2J Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package l2r.gameserver.model.items;

import l2r.gameserver.model.L2ExtractableProduct;
import l2r.gameserver.model.StatsSet;
import l2r.gameserver.model.itemcontainer.Inventory;
import l2r.gameserver.model.items.type.EtcItemType;
import l2r.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is dedicated to the management of EtcItem.
 */
public final class L2EtcItem extends L2Item
{
	private String _handler;
	private EtcItemType _type;
	private final boolean _isBlessed;
	private final List<L2ExtractableProduct> _extractableItems;
	
	private final HashMap<Integer, ArrayList<int[]>> _boxInformation;
	
	/**
	 * Constructor for EtcItem.
	 * @param set StatsSet designating the set of couples (key,value) for description of the Etc
	 */
	public L2EtcItem(StatsSet set)
	{
		super(set);
		_type = set.getEnum("etcitem_type", EtcItemType.class, EtcItemType.NONE);
		
		// l2j custom - L2EtcItemType.SHOT
		switch (getDefaultAction())
		{
			case SOULSHOT:
			case SUMMON_SOULSHOT:
			case SUMMON_SPIRITSHOT:
			case SPIRITSHOT:
			{
				_type = EtcItemType.SHOT;
				break;
			}
		}
		
		_type1 = L2Item.TYPE1_ITEM_QUESTITEM_ADENA;
		_type2 = L2Item.TYPE2_OTHER; // default is other
		
		if (isQuestItem())
		{
			_type2 = L2Item.TYPE2_QUEST;
		}
		else if ((getId() == Inventory.ADENA_ID) || (getId() == Inventory.ANCIENT_ADENA_ID))
		{
			_type2 = L2Item.TYPE2_MONEY;
		}
		
		_handler = set.getString("handler", null); // ! null !
		_isBlessed = set.getBoolean("blessed", false);
		
		// Extractable
		String capsuled_items = set.getString("capsuled_items", null);
		if (capsuled_items != null)
		{
			String[] split = capsuled_items.split(";");
			_extractableItems = new ArrayList<>(split.length);
			for (String part : split)
			{
				if (part.trim().isEmpty())
				{
					continue;
				}
				String[] data = part.split(",");
				if (data.length != 4)
				{
					_log.info(StringUtil.concat("> Couldnt parse ", part, " in capsuled_items! item ", toString()));
					continue;
				}
				int itemId = Integer.parseInt(data[0]);
				int min = Integer.parseInt(data[1]);
				int max = Integer.parseInt(data[2]);
				double chance = Double.parseDouble(data[3]);
				if (max < min)
				{
					_log.info(StringUtil.concat("> Max amount < Min amount in ", part, ", item ", toString()));
					continue;
				}
				L2ExtractableProduct product = new L2ExtractableProduct(itemId, min, max, chance);
				_extractableItems.add(product);
			}
			((ArrayList<?>) _extractableItems).trimToSize();
			
			// check for handler
			if (_handler == null)
			{
				_log.warn("Item " + this + " define capsuled_items but missing handler.");
				_handler = "ExtractableItems";
			}
		}
		else
		{
			_extractableItems = null;
		}
		
		_boxInformation = new HashMap<>();
		String info[] = set.getString("box_information", "").split("/");
		if (info.length > 0)
		{
			for (String base : info)
			{
				if ((base == null) || base.equals(""))
				{
					continue;
				}
				ArrayList<int[]> items = new ArrayList<>();
				String levels[] = base.split("_")[0].split("-");
				String its[] = base.split("_")[1].split(";");
				for (String item : its)
				{
					String splittedItem[] = item.split(",");
					int it[] =
					{
						Integer.parseInt(splittedItem[0]),
						Integer.parseInt(splittedItem[1]),
						Integer.parseInt(splittedItem[2])
					};
					items.add(it);
				}
				for (int level = Integer.parseInt(levels[0]); level <= Integer.parseInt(levels[1]); level++)
				{
					_boxInformation.put(level, items);
				}
			}
		}
	}
	
	/**
	 * @return the type of Etc Item.
	 */
	@Override
	public EtcItemType getItemType()
	{
		return _type;
	}
	
	/**
	 * @return {@code true} if the item is consumable, {@code false} otherwise.
	 */
	@Override
	public final boolean isConsumable()
	{
		return ((getItemType() == EtcItemType.SHOT) || (getItemType() == EtcItemType.POTION)); // || (type == L2EtcItemType.SCROLL));
	}
	
	/**
	 * @return the ID of the Etc item after applying the mask.
	 */
	@Override
	public int getItemMask()
	{
		return getItemType().mask();
	}
	
	/**
	 * @return the handler name, null if no handler for item.
	 */
	public String getHandlerName()
	{
		return _handler;
	}
	
	/**
	 * @return {@code true} if the item is blessed, {@code false} otherwise.
	 */
	public final boolean isBlessed()
	{
		return _isBlessed;
	}
	
	/**
	 * @return the extractable items list.
	 */
	public List<L2ExtractableProduct> getExtractableItems()
	{
		return _extractableItems;
	}
	
	public ArrayList<int[]> getBoxItems(int level)
	{
		if (!_boxInformation.containsKey(level))
		{
			return null;
		}
		return _boxInformation.get(level);
	}
}