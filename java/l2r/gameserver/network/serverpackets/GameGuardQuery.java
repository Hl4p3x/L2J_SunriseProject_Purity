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

/**
 * Lets drink to code!
 * @author zabbix
 */
public class GameGuardQuery extends L2GameServerPacket
{
	public static final GameGuardQuery STATIC_PACKET = new GameGuardQuery();
	
	private GameGuardQuery()
	{
	
	}
	
	@Override
	public void writeImpl()
	{
		switch (ServerTypeConfigs.SERVER_TYPE)
		{
			case IL:
				writeC(0xF9);
				break;
			case GF:
			case EPILOGUE:
			case FREYA:
			case H5:
			case GC:
			case SL:
				writeC(0x74);
				break;
		}
		
		switch (ServerTypeConfigs.SERVER_TYPE)
		{
			case IL:
			case GF:
			case EPILOGUE:
			case FREYA:
			case H5:
				writeD(0x27533DD9);
				writeD(0x2E72A51D);
				writeD(0x2017038B);
				writeD(0xC35B1EA3);
				break;
			case GC:
			case SL:
				writeD(0x27533DD9);
				writeD(0x2E72A51D);
				writeD(0x2017038B);
				writeD(0xC35B1EA3);
				break;
		}
	}
}
