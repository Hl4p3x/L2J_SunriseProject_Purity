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
package l2r.gameserver.model.events.listeners;

import l2r.gameserver.model.events.EventType;
import l2r.gameserver.model.events.ListenersContainer;
import l2r.gameserver.model.events.impl.IBaseEvent;
import l2r.gameserver.model.events.returns.AbstractEventReturn;

import java.util.function.Consumer;

/**
 * Consumer event listener provides callback operation without any return object.
 * @author UnAfraid
 */
public class ConsumerEventListener extends AbstractEventListener
{
	private final Consumer<IBaseEvent> _callback;
	
	@SuppressWarnings("unchecked")
	public ConsumerEventListener(ListenersContainer container, EventType type, Consumer<? extends IBaseEvent> callback, Object owner)
	{
		super(container, type, owner);
		_callback = (Consumer<IBaseEvent>) callback;
	}
	
	@Override
	public <R extends AbstractEventReturn> R executeEvent(IBaseEvent event, Class<R> returnBackClass)
	{
		_callback.accept(event);
		return null;
	}
}
