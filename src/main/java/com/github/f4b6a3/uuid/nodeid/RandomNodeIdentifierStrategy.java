package com.github.f4b6a3.uuid.nodeid;

import java.security.SecureRandom;
import java.util.Random;

import com.github.f4b6a3.uuid.util.NodeIdentifierUtil;
import com.github.f4b6a3.uuid.util.RandomUtil;

public class RandomNodeIdentifierStrategy implements NodeIdentifierStrategy {

	protected Random random;

	public RandomNodeIdentifierStrategy() {
	}
	
	public RandomNodeIdentifierStrategy(Random random) {
		this.random = random;
	}

	/**
	 * Return a new random node identifier for every call.
	 * 
	 * It uses {@link SecureRandom} by default to generate 'cryptographic
	 * quality random number'. The first generated number is returned for all
	 * calls.
	 * 
	 * ### RFC-4122 - 4.1.6. Node
	 * 
	 * For systems with no IEEE address, a randomly or pseudo-randomly generated
	 * value may be used; see Section 4.5. The multicast bit must be set in such
	 * addresses, in order that they will never conflict with addresses obtained
	 * from network cards.
	 * 
	 * ### RFC-4122 - 4.5. Node IDs that Do Not Identify the Host
	 * 
	 * This section describes how to generate a version 1 UUID if an IEEE 802
	 * address is not available, or its use is not desired.
	 * 
	 * A better solution is to obtain a 47-bit cryptographic quality random
	 * number and use it as the low 47 bits of the node ID, with the least
	 * significant bit of the first octet of the node ID set to one. This bit is
	 * the unicast/multicast bit, which will never be set in IEEE 802 addresses
	 * obtained from network cards. Hence, there can never be a conflict between
	 * UUIDs generated by machines with and without network cards. (Recall that
	 * the IEEE 802 spec talks about transmission order, which is the opposite
	 * of the in-memory representation that is discussed in this document.)
	 * 
	 * @return a node identifier
	 */
	@Override
	public long getNodeIdentifier() {
		return getRandomNodeIdentifier();
	}

	/**
	 * Return a random generated node identifier.
	 * 
	 * {@link RandomNodeIdentifierStrategy#getNodeIdentifier()}
	 * 
	 * @return a random multicast node identifier
	 */
	protected long getRandomNodeIdentifier() {
		if (random == null) {
			return NodeIdentifierUtil.setMulticastNodeIdentifier(RandomUtil.nextLong());
		}
		return NodeIdentifierUtil.setMulticastNodeIdentifier(random.nextLong());
	}
}
