package com.github.f4b6a3.uuid.fast.common;

import com.github.f4b6a3.uuid.utils.NumUtils;
import org.apache.commons.codec.binary.Hex;

/**
 * Uuid generator interface.
 *
 * @author sfuser
 */
public interface IUuidGenerator {

    /**
     * Generate a new single entry of the UUID.
     *
     * @return generated single entry.
     */
    Uuid generate();

    /**
     * Generated single uuid entry.
     */
    public static class Uuid {

        private final long lsb;

        private final long msb;

        private byte[] bytes;


        /**
         * CTOR.
         *
         * @param msb most significant bits.
         * @param lsb least significant bits.
         */
        public Uuid(long msb, long lsb) {
            this.msb = msb;
            this.lsb = lsb;
        }

        /**
         * Get the byte value.
         *
         * @return byte value of the current uuid.
         */
        public byte[] bytesValue() {
            if (bytes != null) {
                return bytes;
            }

            byte[] result = new byte[16];

            NumUtils.serializeLongToBytes(msb, result, 0);
            NumUtils.serializeLongToBytes(lsb, result, 8);

            this.bytes = result;

            return result;
        }

        /**
         * Get the hex value of the current uuid.
         *
         * @return hex string of the uuid.
         */
        public String hexValue() {
            return Hex.encodeHexString(bytesValue(), false);
        }

    }

}
