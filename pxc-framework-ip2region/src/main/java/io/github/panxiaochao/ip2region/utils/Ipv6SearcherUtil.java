package io.github.panxiaochao.ip2region.utils;

import io.github.panxiaochao.ip2region.meta.IpInfo;
import io.github.panxiaochao.ip2region.meta.Ipv6Address;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

/**
 * {@code Ipv6Searcher}
 * <p> description: 通过 zxipdb-java 简化改
 *
 * @author Lypxc
 * @since 2023-06-09
 */
public class Ipv6SearcherUtil {
    private final byte[] data;
    private final long total;
    private final long indexStartOffset;
    private final int offLen;
    private final int ipLen;

    public static Ipv6SearcherUtil newWithBuffer(byte[] cBuff) {
        return new Ipv6SearcherUtil(cBuff);
    }

    private Ipv6SearcherUtil(byte[] cBuff) {
        data = cBuff;
        indexStartOffset = read8(16);
        offLen = read1(6);
        ipLen = read1(7);
        total = read8(8);
    }

    public IpInfo query(String ip) {
        return query(new Ipv6Address(ip));
    }

    public IpInfo query(Ipv6Address ip) {
        long ipFind = find(ip, 0, total);
        long ipOffset = indexStartOffset + ipFind * (ipLen + offLen);
        long ipRecordOffset = read8(ipOffset + ipLen, offLen);
        return IpInfoUtil.toIpv6Info(readRecord(ipRecordOffset));
    }

    private long find(Ipv6Address ip, long l, long r) {
        if (l + 1 >= r) {
            return l;
        }
        long m = (l + r) / 2;
        byte[] mip = readRaw(indexStartOffset + m * (ipLen + offLen), ipLen);
        Ipv6Address aip = Ipv6Address.fromBytesV6LE(mip);
        if (ip.compareTo(aip) < 0) {
            return find(ip, l, m);
        } else {
            return find(ip, m, r);
        }
    }

    private String[] readRecord(long offset) {
        String[] recordInfo = new String[2];
        byte[] rec0;
        byte[] rec1;
        int flag = read1(offset);
        if (flag == 1) {
            long locationOffset = read8(offset + 1, offLen);
            return readRecord(locationOffset);
        } else {
            rec0 = readLocation(offset);
            if (flag == 2) {
                rec1 = readLocation(offset + offLen + 1);
            } else {
                rec1 = readLocation(offset + rec0.length + 1);
            }
        }
        recordInfo[0] = new String(rec0, StandardCharsets.UTF_8);
        recordInfo[1] = new String(rec1, StandardCharsets.UTF_8);
        return recordInfo;
    }

    private byte[] readLocation(long offset) {
        if (offset == 0) {
            return new byte[0];
        }
        int flag = read1(offset);
        // 出错
        if (flag == 0) {
            return new byte[0];
        }
        // 仍然为重定向
        if (flag == 2) {
            offset = read8(offset + 1, offLen);
            return readLocation(offset);
        }
        return readStr(offset);
    }

    private byte[] readRaw(byte[] dist, long offset, int size) {
        System.arraycopy(data, (int) offset, dist, 0, size);
        return dist;
    }

    private byte[] readRaw(long offset, int size) {
        byte[] bytes = new byte[size];
        return readRaw(bytes, offset, size);
    }

    private int read1(long offset) {
        return data[(int) (offset)];
    }

    private long read8(long offset) {
        return read8((int) offset, 8);
    }

    private long read8(long offset, int size) {
        byte[] b = new byte[8];
        readRaw(b, offset, size);
        ByteBuffer buffer = ByteBuffer.wrap(b);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.getLong();
    }

    private byte[] readStr(long offset) {
        ByteArrayOutputStream os = new ByteArrayOutputStream(64);
        int ch = read1(offset);
        while (ch != 0) {
            os.write(ch);
            offset++;
            ch = read1(offset);
        }
        return os.toByteArray();
    }
}
