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
package l2r.gameserver.model.conditions;

import l2r.gameserver.model.actor.L2Character;
import l2r.gameserver.model.stats.Env;

public class ConditionTargetHasSkill extends Condition
{
	private final String[] _skills;
	
	public ConditionTargetHasSkill(String[] skills)
	{
		_skills = skills;
	}
	
	@Override
	public boolean testImpl(Env env)
	{
		L2Character target = env.getTarget();
		if (target == null)
		{
			return false;
		}
		for (String skill : _skills)
		{
			if (target.getSkillLevel(Integer.parseInt(skill)) >= 1)
			{
				return true;
			}
		}
		return false;
	}
}