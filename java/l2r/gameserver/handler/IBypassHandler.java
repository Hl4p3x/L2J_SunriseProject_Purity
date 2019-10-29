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
package l2r.gameserver.handler;

import l2r.gameserver.model.actor.L2Character;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author nBd
 */
public interface IBypassHandler
{
	public static Logger _log = LoggerFactory.getLogger(IBypassHandler.class);
	
	/**
	 * This is the worker method that is called when someone uses an bypass command.
	 * @param command
	 * @param activeChar
	 * @param target
	 * @return success
	 */
	public boolean useBypass(String command, L2PcInstance activeChar, L2Character target);
	
	/**
	 * This method is called at initialization to register all bypasses automatically.
	 * @return all known bypasses
	 */
	public String[] getBypassList();
}