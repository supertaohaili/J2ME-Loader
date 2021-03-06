/*
 * Copyright 2018 Nikita Shakarun
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.microemu.cldc.sms;

import org.microemu.microedition.io.ConnectionImplementation;

import java.io.IOException;
import java.io.InterruptedIOException;

import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.MessageListener;

public class Connection implements MessageConnection, ConnectionImplementation {

	private MessageListener listener;

	@Override
	public javax.microedition.io.Connection openConnection(String name, int mode, boolean timeouts) throws IOException {
		return this;
	}

	@Override
	public Message newMessage(String type) {
		return new MessageImpl(type, null);
	}

	@Override
	public Message newMessage(String type, String address) {
		return new MessageImpl(type, address);
	}

	@Override
	public int numberOfSegments(Message message) {
		return 0;
	}

	@Override
	public Message receive() throws IOException, InterruptedIOException {
		MessageImpl message = new MessageImpl(MessageConnection.TEXT_MESSAGE, "addr");
		message.setPayloadText("sms");
		return message;
	}

	@Override
	public void send(Message message) throws IOException, InterruptedIOException {
		if (listener != null) {
			Connection connection = (Connection) openConnection(null, 0, false);
			listener.notifyIncomingMessage(connection);
		}
	}

	@Override
	public void setMessageListener(MessageListener listener) throws IOException {
		this.listener = listener;
	}

	@Override
	public void close() throws IOException {
	}
}
