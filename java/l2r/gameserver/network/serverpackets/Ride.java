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
package l2r.gameserver.network.serverpackets;

import gr.sr.network.handler.ServerTypeConfigs;
import l2r.gameserver.model.Location;
import l2r.gameserver.model.actor.instance.L2PcInstance;

public final class Ride extends L2GameServerPacket
{
	private final int _objectId;
	private final int _mounted;
	private final int _rideType;
	private final int _rideNpcId;
	private final Location _loc;
	
	public Ride(L2PcInstance player)
	{
		_objectId = player.getObjectId();
		_mounted = player.isMounted() ? 1 : 0;
		_rideType = player.getMountType().ordinal();
		_rideNpcId = player.getMountNpcId() + 1000000;
		_loc = player.getLocation();
	}
	
	@Override
	protected final void writeImpl()
	{
		switch (ServerTypeConfigs.SERVER_TYPE)
		{
			case IL:
				writeC(0x86);
				break;
			case GF:
			case EPILOGUE:
			case FREYA:
			case H5:
			case GC:
			case SL:
				writeC(0x8C);
				break;
		}
		
		writeD(_objectId);
		writeD(_mounted);
		writeD(_rideType);
		writeD(_rideNpcId);
		
		switch (ServerTypeConfigs.SERVER_TYPE)
		{
			case GF:
			case EPILOGUE:
			case FREYA:
			case H5:
			case GC:
			case SL:
				writeD(_loc.getX());
				writeD(_loc.getY());
				writeD(_loc.getZ());
				break;
		}
	}
}
