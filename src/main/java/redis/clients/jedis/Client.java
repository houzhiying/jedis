package redis.clients.jedis;

import redis.clients.jedis.JedisCluster.Reset;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;
import redis.clients.util.SafeEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;

import static redis.clients.jedis.Protocol.toByteArray;

public class Client extends BinaryClient implements Commands {

  public Client() {
    super();
  }

  public Client(final String host) {
    super(host);
  }

  public Client(final String host, final int port) {
    super(host, port);
  }

  public Client(final String host, final int port, final boolean ssl) {
    super(host, port, ssl);
  }

  public Client(final String host, final int port, final boolean ssl,
      final SSLSocketFactory sslSocketFactory, final SSLParameters sslParameters,
      final HostnameVerifier hostnameVerifier) {
    super(host, port, ssl, sslSocketFactory, sslParameters, hostnameVerifier);
  }

  @Override
  public void set(final String key, final String value) {
    set(SafeEncoder.encode(key), SafeEncoder.encode(value));
  }

  public void set(final String key, final String value, final String nxxx, final String expx,
      final long time) {
    set(SafeEncoder.encode(key), SafeEncoder.encode(value), SafeEncoder.encode(nxxx),
        SafeEncoder.encode(expx), time);
  }

  public void get(final String key) {
    get(SafeEncoder.encode(key));
  }

  public void exists(final String key) {
    exists(SafeEncoder.encode(key));
  }

  public void exists(final String... keys) {
    final byte[][] bkeys = SafeEncoder.encodeMany(keys);
    exists(bkeys);
  }

  public void del(final String... keys) {
    final byte[][] bkeys = new byte[keys.length][];
    for (int i = 0; i < keys.length; i++) {
      bkeys[i] = SafeEncoder.encode(keys[i]);
    }
    del(bkeys);
  }

  public void type(final String key) {
    type(SafeEncoder.encode(key));
  }

  public void keys(final String pattern) {
    keys(SafeEncoder.encode(pattern));
  }

  public void rename(final String oldkey, final String newkey) {
    rename(SafeEncoder.encode(oldkey), SafeEncoder.encode(newkey));
  }

  public void renamenx(final String oldkey, final String newkey) {
    renamenx(SafeEncoder.encode(oldkey), SafeEncoder.encode(newkey));
  }

  public void expire(final String key, final int seconds) {
    expire(SafeEncoder.encode(key), seconds);
  }

  public void expireAt(final String key, final long unixTime) {
    expireAt(SafeEncoder.encode(key), unixTime);
  }

  public void ttl(final String key) {
    ttl(SafeEncoder.encode(key));
  }

  public void move(final String key, final int dbIndex) {
    move(SafeEncoder.encode(key), dbIndex);
  }

  public void getSet(final String key, final String value) {
    getSet(SafeEncoder.encode(key), SafeEncoder.encode(value));
  }

  public void mget(final String... keys) {
    final byte[][] bkeys = new byte[keys.length][];
    for (int i = 0; i < bkeys.length; i++) {
      bkeys[i] = SafeEncoder.encode(keys[i]);
    }
    mget(bkeys);
  }

  public void setnx(final String key, final String value) {
    setnx(SafeEncoder.encode(key), SafeEncoder.encode(value));
  }

  public void setex(final String key, final int seconds, final String value) {
    setex(SafeEncoder.encode(key), seconds, SafeEncoder.encode(value));
  }

  public void mset(final String... keysvalues) {
    final byte[][] bkeysvalues = new byte[keysvalues.length][];
    for (int i = 0; i < keysvalues.length; i++) {
      bkeysvalues[i] = SafeEncoder.encode(keysvalues[i]);
    }
    mset(bkeysvalues);
  }

  public void msetnx(final String... keysvalues) {
    final byte[][] bkeysvalues = new byte[keysvalues.length][];
    for (int i = 0; i < keysvalues.length; i++) {
      bkeysvalues[i] = SafeEncoder.encode(keysvalues[i]);
    }
    msetnx(bkeysvalues);
  }

  public void decrBy(final String key, final long integer) {
    decrBy(SafeEncoder.encode(key), integer);
  }

  public void decr(final String key) {
    decr(SafeEncoder.encode(key));
  }

  public void incrBy(final String key, final long integer) {
    incrBy(SafeEncoder.encode(key), integer);
  }

  public void incr(final String key) {
    incr(SafeEncoder.encode(key));
  }

  public void append(final String key, final String value) {
    append(SafeEncoder.encode(key), SafeEncoder.encode(value));
  }

  public void substr(final String key, final int start, final int end) {
    substr(SafeEncoder.encode(key), start, end);
  }

  public void hset(final String key, final String field, final String value) {
    hset(SafeEncoder.encode(key), SafeEncoder.encode(field), SafeEncoder.encode(value));
  }

  public void hget(final String key, final String field) {
    hget(SafeEncoder.encode(key), SafeEncoder.encode(field));
  }

  public void hsetnx(final String key, final String field, final String value) {
    hsetnx(SafeEncoder.encode(key), SafeEncoder.encode(field), SafeEncoder.encode(value));
  }

  public void hsetex(final String key, final Long expire, final String field, final String value) {
    hsetex(SafeEncoder.encode(key), expire, SafeEncoder.encode(field), SafeEncoder.encode(value));
  }

  public void hmsetex(final String key, final Long expire, final Map<String, String> hash) {
    final Map<byte[], byte[]> bhash = new HashMap<byte[], byte[]>(hash.size());
    for (final Entry<String, String> entry : hash.entrySet()) {
      bhash.put(SafeEncoder.encode(entry.getKey()), SafeEncoder.encode(entry.getValue()));
    }
    hmsetex(SafeEncoder.encode(key),expire, bhash);
  }

  public void hgetex(final String key, final Long expire, final String field) {
    hgetex(SafeEncoder.encode(key), expire, SafeEncoder.encode(field));
  }

  public void hdelex(final String key, final Long expire, final String field) {
    hdelex(SafeEncoder.encode(key), expire, SafeEncoder.encode(field));
  }

  public void hmset(final String key, final Map<String, String> hash) {
    final Map<byte[], byte[]> bhash = new HashMap<byte[], byte[]>(hash.size());
    for (final Entry<String, String> entry : hash.entrySet()) {
      bhash.put(SafeEncoder.encode(entry.getKey()), SafeEncoder.encode(entry.getValue()));
    }
    hmset(SafeEncoder.encode(key), bhash);
  }

  public void hmget(final String key, final String... fields) {
    final byte[][] bfields = new byte[fields.length][];
    for (int i = 0; i < bfields.length; i++) {
      bfields[i] = SafeEncoder.encode(fields[i]);
    }
    hmget(SafeEncoder.encode(key), bfields);
  }

  public void hincrBy(final String key, final String field, final long value) {
    hincrBy(SafeEncoder.encode(key), SafeEncoder.encode(field), value);
  }

  public void hexists(final String key, final String field) {
    hexists(SafeEncoder.encode(key), SafeEncoder.encode(field));
  }

  public void hdel(final String key, final String... fields) {
    hdel(SafeEncoder.encode(key), SafeEncoder.encodeMany(fields));
  }

  public void hlen(final String key) {
    hlen(SafeEncoder.encode(key));
  }

  public void hkeys(final String key) {
    hkeys(SafeEncoder.encode(key));
  }

  public void hvals(final String key) {
    hvals(SafeEncoder.encode(key));
  }

  public void hgetAll(final String key) {
    hgetAll(SafeEncoder.encode(key));
  }

  public void hclear(final String key) {
    hclear(SafeEncoder.encode(key));
  }

  public void hexpire(final String key, final int seconds) {
    hexpire(SafeEncoder.encode(key), seconds);
  }

  public void httl(final String key) {
    httl(SafeEncoder.encode(key));
  }

  public void hpersist(final String key) {
    hpersist(SafeEncoder.encode(key));
  }

  public void rpush(final String key, final String... string) {
    rpush(SafeEncoder.encode(key), SafeEncoder.encodeMany(string));
  }

  public void lpush(final String key, final String... string) {
    lpush(SafeEncoder.encode(key), SafeEncoder.encodeMany(string));
  }

  public void llen(final String key) {
    llen(SafeEncoder.encode(key));
  }

  public void lrange(final String key, final long start, final long end) {
    lrange(SafeEncoder.encode(key), start, end);
  }

  public void ltrim(final String key, final long start, final long end) {
    ltrim(SafeEncoder.encode(key), start, end);
  }

  public void lindex(final String key, final long index) {
    lindex(SafeEncoder.encode(key), index);
  }

  public void lset(final String key, final long index, final String value) {
    lset(SafeEncoder.encode(key), index, SafeEncoder.encode(value));
  }

  public void lrem(final String key, long count, final String value) {
    lrem(SafeEncoder.encode(key), count, SafeEncoder.encode(value));
  }

  public void lpop(final String key) {
    lpop(SafeEncoder.encode(key));
  }

  public void lclear(final String key) {
    lclear(SafeEncoder.encode(key));
  }

  public void lexpire(final String key, final int seconds) {
    lexpire(SafeEncoder.encode(key), seconds);
  }

  public void lttl(final String key) {
    lttl(SafeEncoder.encode(key));
  }

  public void lpersist(final String key) {
    lpersist(SafeEncoder.encode(key));
  }

  public void rpop(final String key) {
    rpop(SafeEncoder.encode(key));
  }

  public void rpoplpush(final String srckey, final String dstkey) {
    rpoplpush(SafeEncoder.encode(srckey), SafeEncoder.encode(dstkey));
  }

  public void sadd(final String key, final String... members) {
    sadd(SafeEncoder.encode(key), SafeEncoder.encodeMany(members));
  }

  public void smembers(final String key) {
    smembers(SafeEncoder.encode(key));
  }

  public void srem(final String key, final String... members) {
    srem(SafeEncoder.encode(key), SafeEncoder.encodeMany(members));
  }

  public void spop(final String key) {
    spop(SafeEncoder.encode(key));
  }

  public void spop(final String key, final long count) {
    spop(SafeEncoder.encode(key), count);
  }

  public void smove(final String srckey, final String dstkey, final String member) {
    smove(SafeEncoder.encode(srckey), SafeEncoder.encode(dstkey), SafeEncoder.encode(member));
  }

  public void scard(final String key) {
    scard(SafeEncoder.encode(key));
  }

  public void sismember(final String key, final String member) {
    sismember(SafeEncoder.encode(key), SafeEncoder.encode(member));
  }

  public void sinter(final String... keys) {
    final byte[][] bkeys = new byte[keys.length][];
    for (int i = 0; i < bkeys.length; i++) {
      bkeys[i] = SafeEncoder.encode(keys[i]);
    }
    sinter(bkeys);
  }

  public void sinterstore(final String dstkey, final String... keys) {
    final byte[][] bkeys = new byte[keys.length][];
    for (int i = 0; i < bkeys.length; i++) {
      bkeys[i] = SafeEncoder.encode(keys[i]);
    }
    sinterstore(SafeEncoder.encode(dstkey), bkeys);
  }

  public void sunion(final String... keys) {
    final byte[][] bkeys = new byte[keys.length][];
    for (int i = 0; i < bkeys.length; i++) {
      bkeys[i] = SafeEncoder.encode(keys[i]);
    }
    sunion(bkeys);
  }

  public void sunionstore(final String dstkey, final String... keys) {
    final byte[][] bkeys = new byte[keys.length][];
    for (int i = 0; i < bkeys.length; i++) {
      bkeys[i] = SafeEncoder.encode(keys[i]);
    }
    sunionstore(SafeEncoder.encode(dstkey), bkeys);
  }

  public void sdiff(final String... keys) {
    final byte[][] bkeys = new byte[keys.length][];
    for (int i = 0; i < bkeys.length; i++) {
      bkeys[i] = SafeEncoder.encode(keys[i]);
    }
    sdiff(bkeys);
  }

  public void sdiffstore(final String dstkey, final String... keys) {
    final byte[][] bkeys = new byte[keys.length][];
    for (int i = 0; i < bkeys.length; i++) {
      bkeys[i] = SafeEncoder.encode(keys[i]);
    }
    sdiffstore(SafeEncoder.encode(dstkey), bkeys);
  }

  public void srandmember(final String key) {
    srandmember(SafeEncoder.encode(key));
  }

  public void sclear(final String key) {
    sclear(SafeEncoder.encode(key));
  }

  public void smclear(final String... key) {
    smclear(SafeEncoder.encodeMany(key));
  }

  public void sexpire(final String key, final int seconds) {
    sexpire(SafeEncoder.encode(key), seconds);
  }

  public void sttl(final String key) {
    sttl(SafeEncoder.encode(key));
  }

  public void spersist(final String key) {
    spersist(SafeEncoder.encode(key));
  }

  public void zadd(final String key, final double score, final String member) {
    zadd(SafeEncoder.encode(key), score, SafeEncoder.encode(member));
  }

  @Override
  public void zadd(final String key, final double score, final String member,
      final ZAddParams params) {
    zadd(SafeEncoder.encode(key), score, SafeEncoder.encode(member), params);
  }

  @Override
  public void zadd(String key, Map<String, Double> scoreMembers) {
    HashMap<byte[], Double> binaryScoreMembers = convertScoreMembersToBinary(scoreMembers);
    zaddBinary(SafeEncoder.encode(key), binaryScoreMembers);
  }

  @Override
  public void zadd(final String key, final Map<String, Double> scoreMembers,
      final ZAddParams params) {
    HashMap<byte[], Double> binaryScoreMembers = convertScoreMembersToBinary(scoreMembers);
    zaddBinary(SafeEncoder.encode(key), binaryScoreMembers, params);
  }

  @Override
  public void zrange(final String key, final long start, final long end) {
    zrange(SafeEncoder.encode(key), start, end);
  }

  public void zrem(final String key, final String... members) {
    zrem(SafeEncoder.encode(key), SafeEncoder.encodeMany(members));
  }

  public void zincrby(final String key, final double score, final String member) {
    zincrby(SafeEncoder.encode(key), score, SafeEncoder.encode(member));
  }

  @Override
  public void zincrby(String key, double score, String member, ZIncrByParams params) {
    zincrby(SafeEncoder.encode(key), score, SafeEncoder.encode(member), params);
  }

  @Override
  public void zrank(final String key, final String member) {
    zrank(SafeEncoder.encode(key), SafeEncoder.encode(member));
  }

  public void zrevrank(final String key, final String member) {
    zrevrank(SafeEncoder.encode(key), SafeEncoder.encode(member));
  }

  public void zrevrange(final String key, final long start, final long end) {
    zrevrange(SafeEncoder.encode(key), start, end);
  }

  public void zrangeWithScores(final String key, final long start, final long end) {
    zrangeWithScores(SafeEncoder.encode(key), start, end);
  }

  public void zrevrangeWithScores(final String key, final long start, final long end) {
    zrevrangeWithScores(SafeEncoder.encode(key), start, end);
  }

  public void zcard(final String key) {
    zcard(SafeEncoder.encode(key));
  }

  public void zscore(final String key, final String member) {
    zscore(SafeEncoder.encode(key), SafeEncoder.encode(member));
  }

  public void zclear(final String key) {
    zclear(SafeEncoder.encode(key));
  }

  public void zexpire(final String key, final int seconds) {
    zexpire(SafeEncoder.encode(key), seconds);
  }

  public void zttl(final String key) {
    zttl(SafeEncoder.encode(key));
  }

  public void zpersist(final String key) {
    zpersist(SafeEncoder.encode(key));
  }

  public void watch(final String... keys) {
    final byte[][] bargs = new byte[keys.length][];
    for (int i = 0; i < bargs.length; i++) {
      bargs[i] = SafeEncoder.encode(keys[i]);
    }
    watch(bargs);
  }

  public void sort(final String key) {
    sort(SafeEncoder.encode(key));
  }

  public void sort(final String key, final SortingParams sortingParameters) {
    sort(SafeEncoder.encode(key), sortingParameters);
  }

  public void blpop(final String[] args) {
    final byte[][] bargs = new byte[args.length][];
    for (int i = 0; i < bargs.length; i++) {
      bargs[i] = SafeEncoder.encode(args[i]);
    }
    blpop(bargs);
  }

  public void blpop(final int timeout, final String... keys) {
    final int size = keys.length + 1;
    List<String> args = new ArrayList<String>(size);
    for (String arg : keys) {
      args.add(arg);
    }
    args.add(String.valueOf(timeout));
    blpop(args.toArray(new String[size]));
  }

  public void sort(final String key, final SortingParams sortingParameters, final String dstkey) {
    sort(SafeEncoder.encode(key), sortingParameters, SafeEncoder.encode(dstkey));
  }

  public void sort(final String key, final String dstkey) {
    sort(SafeEncoder.encode(key), SafeEncoder.encode(dstkey));
  }

  public void brpop(final String[] args) {
    final byte[][] bargs = new byte[args.length][];
    for (int i = 0; i < bargs.length; i++) {
      bargs[i] = SafeEncoder.encode(args[i]);
    }
    brpop(bargs);
  }

  public void brpop(final int timeout, final String... keys) {
    final int size = keys.length + 1;
    List<String> args = new ArrayList<String>(size);
    for (String arg : keys) {
      args.add(arg);
    }
    args.add(String.valueOf(timeout));
    brpop(args.toArray(new String[size]));
  }

  public void zcount(final String key, final double min, final double max) {
    zcount(SafeEncoder.encode(key), toByteArray(min), toByteArray(max));
  }

  public void zcount(final String key, final String min, final String max) {
    zcount(SafeEncoder.encode(key), SafeEncoder.encode(min), SafeEncoder.encode(max));
  }

  public void zrangeByScore(final String key, final double min, final double max) {
    zrangeByScore(SafeEncoder.encode(key), toByteArray(min), toByteArray(max));
  }

  public void zrangeByScore(final String key, final String min, final String max) {
    zrangeByScore(SafeEncoder.encode(key), SafeEncoder.encode(min), SafeEncoder.encode(max));
  }

  public void zrangeByScore(final String key, final double min, final double max, final int offset,
      int count) {
    zrangeByScore(SafeEncoder.encode(key), toByteArray(min), toByteArray(max), offset, count);
  }

  public void zrangeByScoreWithScores(final String key, final double min, final double max) {
    zrangeByScoreWithScores(SafeEncoder.encode(key), toByteArray(min), toByteArray(max));
  }

  public void zrangeByScoreWithScores(final String key, final double min, final double max,
      final int offset, final int count) {
    zrangeByScoreWithScores(SafeEncoder.encode(key), toByteArray(min), toByteArray(max), offset,
        count);
  }

  public void zrevrangeByScore(final String key, final double max, final double min) {
    zrevrangeByScore(SafeEncoder.encode(key), toByteArray(max), toByteArray(min));
  }

  public void zrangeByScore(final String key, final String min, final String max, final int offset,
      int count) {
    zrangeByScore(SafeEncoder.encode(key), SafeEncoder.encode(min), SafeEncoder.encode(max),
        offset, count);
  }

  public void zrangeByScoreWithScores(final String key, final String min, final String max) {
    zrangeByScoreWithScores(SafeEncoder.encode(key), SafeEncoder.encode(min),
        SafeEncoder.encode(max));
  }

  public void zrangeByScoreWithScores(final String key, final String min, final String max,
      final int offset, final int count) {
    zrangeByScoreWithScores(SafeEncoder.encode(key), SafeEncoder.encode(min),
        SafeEncoder.encode(max), offset, count);
  }

  public void zrevrangeByScore(final String key, final String max, final String min) {
    zrevrangeByScore(SafeEncoder.encode(key), SafeEncoder.encode(max), SafeEncoder.encode(min));
  }

  public void zrevrangeByScore(final String key, final double max, final double min,
      final int offset, int count) {
    zrevrangeByScore(SafeEncoder.encode(key), toByteArray(max), toByteArray(min), offset, count);
  }

  public void zrevrangeByScore(final String key, final String max, final String min,
      final int offset, int count) {
    zrevrangeByScore(SafeEncoder.encode(key), SafeEncoder.encode(max), SafeEncoder.encode(min),
        offset, count);
  }

  public void zrevrangeByScoreWithScores(final String key, final double max, final double min) {
    zrevrangeByScoreWithScores(SafeEncoder.encode(key), toByteArray(max), toByteArray(min));
  }

  public void zrevrangeByScoreWithScores(final String key, final String max, final String min) {
    zrevrangeByScoreWithScores(SafeEncoder.encode(key), SafeEncoder.encode(max),
        SafeEncoder.encode(min));
  }

  public void zrevrangeByScoreWithScores(final String key, final double max, final double min,
      final int offset, final int count) {
    zrevrangeByScoreWithScores(SafeEncoder.encode(key), toByteArray(max), toByteArray(min), offset,
        count);
  }

  public void zrevrangeByScoreWithScores(final String key, final String max, final String min,
      final int offset, final int count) {
    zrevrangeByScoreWithScores(SafeEncoder.encode(key), SafeEncoder.encode(max),
        SafeEncoder.encode(min), offset, count);
  }

  public void zremrangeByRank(final String key, final long start, final long end) {
    zremrangeByRank(SafeEncoder.encode(key), start, end);
  }

  public void zremrangeByScore(final String key, final double start, final double end) {
    zremrangeByScore(SafeEncoder.encode(key), toByteArray(start), toByteArray(end));
  }

  public void zremrangeByScore(final String key, final String start, final String end) {
    zremrangeByScore(SafeEncoder.encode(key), SafeEncoder.encode(start), SafeEncoder.encode(end));
  }

  public void zunionstore(final String dstkey, final String... sets) {
    final byte[][] bsets = new byte[sets.length][];
    for (int i = 0; i < bsets.length; i++) {
      bsets[i] = SafeEncoder.encode(sets[i]);
    }
    zunionstore(SafeEncoder.encode(dstkey), bsets);
  }

  public void zunionstore(final String dstkey, final ZParams params, final String... sets) {
    final byte[][] bsets = new byte[sets.length][];
    for (int i = 0; i < bsets.length; i++) {
      bsets[i] = SafeEncoder.encode(sets[i]);
    }
    zunionstore(SafeEncoder.encode(dstkey), params, bsets);
  }

  public void zinterstore(final String dstkey, final String... sets) {
    final byte[][] bsets = new byte[sets.length][];
    for (int i = 0; i < bsets.length; i++) {
      bsets[i] = SafeEncoder.encode(sets[i]);
    }
    zinterstore(SafeEncoder.encode(dstkey), bsets);
  }

  public void zinterstore(final String dstkey, final ZParams params, final String... sets) {
    final byte[][] bsets = new byte[sets.length][];
    for (int i = 0; i < bsets.length; i++) {
      bsets[i] = SafeEncoder.encode(sets[i]);
    }
    zinterstore(SafeEncoder.encode(dstkey), params, bsets);
  }

  public void zlexcount(final String key, final String min, final String max) {
    zlexcount(SafeEncoder.encode(key), SafeEncoder.encode(min), SafeEncoder.encode(max));
  }

  public void zrangeByLex(final String key, final String min, final String max) {
    zrangeByLex(SafeEncoder.encode(key), SafeEncoder.encode(min), SafeEncoder.encode(max));
  }

  public void zrangeByLex(final String key, final String min, final String max, final int offset,
      final int count) {
    zrangeByLex(SafeEncoder.encode(key), SafeEncoder.encode(min), SafeEncoder.encode(max), offset,
        count);
  }

  public void zrevrangeByLex(String key, String max, String min) {
    zrevrangeByLex(SafeEncoder.encode(key), SafeEncoder.encode(max), SafeEncoder.encode(min));
  }

  public void zrevrangeByLex(String key, String max, String min, int offset, int count) {
    zrevrangeByLex(SafeEncoder.encode(key), SafeEncoder.encode(max), SafeEncoder.encode(min),
        offset, count);
  }

  public void zremrangeByLex(final String key, final String min, final String max) {
    zremrangeByLex(SafeEncoder.encode(key), SafeEncoder.encode(min), SafeEncoder.encode(max));
  }

  public void strlen(final String key) {
    strlen(SafeEncoder.encode(key));
  }

  public void lpushx(final String key, final String... string) {
    lpushx(SafeEncoder.encode(key), getByteParams(string));
  }

  public void persist(final String key) {
    persist(SafeEncoder.encode(key));
  }

  public void rpushx(final String key, final String... string) {
    rpushx(SafeEncoder.encode(key), getByteParams(string));
  }

  public void echo(final String string) {
    echo(SafeEncoder.encode(string));
  }

  public void linsert(final String key, final LIST_POSITION where, final String pivot,
      final String value) {
    linsert(SafeEncoder.encode(key), where, SafeEncoder.encode(pivot), SafeEncoder.encode(value));
  }

  public void brpoplpush(String source, String destination, int timeout) {
    brpoplpush(SafeEncoder.encode(source), SafeEncoder.encode(destination), timeout);
  }

  public void setbit(final String key, final long offset, final boolean value) {
    setbit(SafeEncoder.encode(key), offset, value);
  }

  public void setbit(final String key, final long offset, final String value) {
    setbit(SafeEncoder.encode(key), offset, SafeEncoder.encode(value));
  }

  public void getbit(String key, long offset) {
    getbit(SafeEncoder.encode(key), offset);
  }

  public void bitpos(final String key, final boolean value, final BitPosParams params) {
    bitpos(SafeEncoder.encode(key), value, params);
  }

  public void setrange(String key, long offset, String value) {
    setrange(SafeEncoder.encode(key), offset, SafeEncoder.encode(value));
  }

  public void getrange(String key, long startOffset, long endOffset) {
    getrange(SafeEncoder.encode(key), startOffset, endOffset);
  }

  public void publish(final String channel, final String message) {
    publish(SafeEncoder.encode(channel), SafeEncoder.encode(message));
  }

  public void unsubscribe(final String... channels) {
    final byte[][] cs = new byte[channels.length][];
    for (int i = 0; i < cs.length; i++) {
      cs[i] = SafeEncoder.encode(channels[i]);
    }
    unsubscribe(cs);
  }

  public void psubscribe(final String... patterns) {
    final byte[][] ps = new byte[patterns.length][];
    for (int i = 0; i < ps.length; i++) {
      ps[i] = SafeEncoder.encode(patterns[i]);
    }
    psubscribe(ps);
  }

  public void punsubscribe(final String... patterns) {
    final byte[][] ps = new byte[patterns.length][];
    for (int i = 0; i < ps.length; i++) {
      ps[i] = SafeEncoder.encode(patterns[i]);
    }
    punsubscribe(ps);
  }

  public void subscribe(final String... channels) {
    final byte[][] cs = new byte[channels.length][];
    for (int i = 0; i < cs.length; i++) {
      cs[i] = SafeEncoder.encode(channels[i]);
    }
    subscribe(cs);
  }

  public void pubsubChannels(String pattern) {
    pubsub(Protocol.PUBSUB_CHANNELS, pattern);
  }

  public void pubsubNumPat() {
    pubsub(Protocol.PUBSUB_NUM_PAT);
  }

  public void pubsubNumSub(String... channels) {
    pubsub(Protocol.PUBSUB_NUMSUB, channels);
  }

  public void configSet(String parameter, String value) {
    configSet(SafeEncoder.encode(parameter), SafeEncoder.encode(value));
  }

  public void configGet(String pattern) {
    configGet(SafeEncoder.encode(pattern));
  }

  public void eval(String script, int keyCount, String... params) {
    eval(SafeEncoder.encode(script), toByteArray(keyCount), getByteParams(params));
  }

  public void evalsha(String sha1, int keyCount, String... params) {
    evalsha(SafeEncoder.encode(sha1), toByteArray(keyCount), getByteParams(params));
  }

  public void scriptExists(String... sha1) {
    final byte[][] bsha1 = new byte[sha1.length][];
    for (int i = 0; i < bsha1.length; i++) {
      bsha1[i] = SafeEncoder.encode(sha1[i]);
    }
    scriptExists(bsha1);
  }

  public void scriptLoad(String script) {
    scriptLoad(SafeEncoder.encode(script));
  }

  @Override
  public void objectRefcount(String key) {
    objectRefcount(SafeEncoder.encode(key));
  }

  public void objectIdletime(String key) {
    objectIdletime(SafeEncoder.encode(key));
  }

  public void objectEncoding(String key) {
    objectEncoding(SafeEncoder.encode(key));
  }

  public void bitcount(final String key) {
    bitcount(SafeEncoder.encode(key));
  }

  public void bitcount(final String key, long start, long end) {
    bitcount(SafeEncoder.encode(key), start, end);
  }

  public void bitop(BitOP op, final String destKey, String... srcKeys) {
    bitop(op, SafeEncoder.encode(destKey), getByteParams(srcKeys));
  }

  public void sentinel(final String... args) {
    final byte[][] arg = new byte[args.length][];
    for (int i = 0; i < arg.length; i++) {
      arg[i] = SafeEncoder.encode(args[i]);
    }
    sentinel(arg);
  }

  public void dump(final String key) {
    dump(SafeEncoder.encode(key));
  }

  public void restore(final String key, final int ttl, final byte[] serializedValue) {
    restore(SafeEncoder.encode(key), ttl, serializedValue);
  }

  @Deprecated
  public void pexpire(final String key, final int milliseconds) {
    pexpire(key, (long) milliseconds);
  }

  public void pexpire(final String key, final long milliseconds) {
    pexpire(SafeEncoder.encode(key), milliseconds);
  }

  public void pexpireAt(final String key, final long millisecondsTimestamp) {
    pexpireAt(SafeEncoder.encode(key), millisecondsTimestamp);
  }

  public void pttl(final String key) {
    pttl(SafeEncoder.encode(key));
  }

  public void incrByFloat(final String key, final double increment) {
    incrByFloat(SafeEncoder.encode(key), increment);
  }

  @Deprecated
  public void psetex(final String key, final int milliseconds, final String value) {
    psetex(key, (long) milliseconds, value);
  }

  public void psetex(final String key, final long milliseconds, final String value) {
    psetex(SafeEncoder.encode(key), milliseconds, SafeEncoder.encode(value));
  }

  public void set(final String key, final String value, final String nxxx) {
    set(SafeEncoder.encode(key), SafeEncoder.encode(value), SafeEncoder.encode(nxxx));
  }

  public void set(final String key, final String value, final String nxxx, final String expx,
      final int time) {
    set(SafeEncoder.encode(key), SafeEncoder.encode(value), SafeEncoder.encode(nxxx),
        SafeEncoder.encode(expx), time);
  }

  public void srandmember(final String key, final int count) {
    srandmember(SafeEncoder.encode(key), count);
  }

  public void clientKill(final String client) {
    clientKill(SafeEncoder.encode(client));
  }

  public void clientSetname(final String name) {
    clientSetname(SafeEncoder.encode(name));
  }

  public void migrate(final String host, final int port, final String key, final int destinationDb,
      final int timeout) {
    migrate(SafeEncoder.encode(host), port, SafeEncoder.encode(key), destinationDb, timeout);
  }

  public void hincrByFloat(final String key, final String field, double increment) {
    hincrByFloat(SafeEncoder.encode(key), SafeEncoder.encode(field), increment);
  }

  @Deprecated
  /**
   * This method is deprecated due to bug (scan cursor should be unsigned long)
   * And will be removed on next major release
   * @see https://github.com/xetorthio/jedis/issues/531
   */
  public void hscan(final String key, int cursor, final ScanParams params) {
    hscan(SafeEncoder.encode(key), cursor, params);
  }

  @Deprecated
  /**
   * This method is deprecated due to bug (scan cursor should be unsigned long)
   * And will be removed on next major release
   * @see https://github.com/xetorthio/jedis/issues/531
   */
  public void sscan(final String key, int cursor, final ScanParams params) {
    sscan(SafeEncoder.encode(key), cursor, params);
  }

  @Deprecated
  /**
   * This method is deprecated due to bug (scan cursor should be unsigned long)
   * And will be removed on next major release
   * @see https://github.com/xetorthio/jedis/issues/531
   */
  public void zscan(final String key, int cursor, final ScanParams params) {
    zscan(SafeEncoder.encode(key), cursor, params);
  }

  public void scan(final String cursor, final ScanParams params) {
    scan(SafeEncoder.encode(cursor), params);
  }

  public void hscan(final String key, final String cursor, final ScanParams params) {
    hscan(SafeEncoder.encode(key), SafeEncoder.encode(cursor), params);
  }

  public void sscan(final String key, final String cursor, final ScanParams params) {
    sscan(SafeEncoder.encode(key), SafeEncoder.encode(cursor), params);
  }

  public void zscan(final String key, final String cursor, final ScanParams params) {
    zscan(SafeEncoder.encode(key), SafeEncoder.encode(cursor), params);
  }

  public void cluster(final String subcommand, final int... args) {
    final byte[][] arg = new byte[args.length + 1][];
    for (int i = 1; i < arg.length; i++) {
      arg[i] = toByteArray(args[i - 1]);
    }
    arg[0] = SafeEncoder.encode(subcommand);
    cluster(arg);
  }

  public void pubsub(final String subcommand, final String... args) {
    final byte[][] arg = new byte[args.length + 1][];
    for (int i = 1; i < arg.length; i++) {
      arg[i] = SafeEncoder.encode(args[i - 1]);
    }
    arg[0] = SafeEncoder.encode(subcommand);
    pubsub(arg);
  }

  public void cluster(final String subcommand, final String... args) {
    final byte[][] arg = new byte[args.length + 1][];
    for (int i = 1; i < arg.length; i++) {
      arg[i] = SafeEncoder.encode(args[i - 1]);
    }
    arg[0] = SafeEncoder.encode(subcommand);
    cluster(arg);
  }

  public void cluster(final String subcommand) {
    final byte[][] arg = new byte[1][];
    arg[0] = SafeEncoder.encode(subcommand);
    cluster(arg);
  }

  public void clusterNodes() {
    cluster(Protocol.CLUSTER_NODES);
  }

  public void clusterMeet(final String ip, final int port) {
    cluster(Protocol.CLUSTER_MEET, ip, String.valueOf(port));
  }

  public void clusterReset(Reset resetType) {
    cluster(Protocol.CLUSTER_RESET, resetType.toString());
  }

  public void clusterAddSlots(final int... slots) {
    cluster(Protocol.CLUSTER_ADDSLOTS, slots);
  }

  public void clusterDelSlots(final int... slots) {
    cluster(Protocol.CLUSTER_DELSLOTS, slots);
  }

  public void clusterInfo() {
    cluster(Protocol.CLUSTER_INFO);
  }

  public void clusterGetKeysInSlot(final int slot, final int count) {
    final int[] args = new int[]{slot, count};
    cluster(Protocol.CLUSTER_GETKEYSINSLOT, args);
  }

  public void clusterSetSlotNode(final int slot, final String nodeId) {
    cluster(Protocol.CLUSTER_SETSLOT, String.valueOf(slot), Protocol.CLUSTER_SETSLOT_NODE, nodeId);
  }

  public void clusterSetSlotMigrating(final int slot, final String nodeId) {
    cluster(Protocol.CLUSTER_SETSLOT, String.valueOf(slot), Protocol.CLUSTER_SETSLOT_MIGRATING,
        nodeId);
  }

  public void clusterSetSlotImporting(final int slot, final String nodeId) {
    cluster(Protocol.CLUSTER_SETSLOT, String.valueOf(slot), Protocol.CLUSTER_SETSLOT_IMPORTING,
        nodeId);
  }

  public void pfadd(String key, final String... elements) {
    pfadd(SafeEncoder.encode(key), SafeEncoder.encodeMany(elements));
  }

  public void pfcount(final String key) {
    pfcount(SafeEncoder.encode(key));
  }

  public void pfcount(final String... keys) {
    pfcount(SafeEncoder.encodeMany(keys));
  }

  public void pfmerge(final String destkey, final String... sourcekeys) {
    pfmerge(SafeEncoder.encode(destkey), SafeEncoder.encodeMany(sourcekeys));
  }

  public void clusterSetSlotStable(final int slot) {
    cluster(Protocol.CLUSTER_SETSLOT, String.valueOf(slot), Protocol.CLUSTER_SETSLOT_STABLE);
  }

  public void clusterForget(final String nodeId) {
    cluster(Protocol.CLUSTER_FORGET, nodeId);
  }

  public void clusterFlushSlots() {
    cluster(Protocol.CLUSTER_FLUSHSLOT);
  }

  public void clusterKeySlot(final String key) {
    cluster(Protocol.CLUSTER_KEYSLOT, key);
  }

  public void clusterCountKeysInSlot(final int slot) {
    cluster(Protocol.CLUSTER_COUNTKEYINSLOT, String.valueOf(slot));
  }

  public void clusterSaveConfig() {
    cluster(Protocol.CLUSTER_SAVECONFIG);
  }

  public void clusterReplicate(final String nodeId) {
    cluster(Protocol.CLUSTER_REPLICATE, nodeId);
  }

  public void clusterSlaves(final String nodeId) {
    cluster(Protocol.CLUSTER_SLAVES, nodeId);
  }

  public void clusterFailover() {
    cluster(Protocol.CLUSTER_FAILOVER);
  }

  public void clusterSlots() {
    cluster(Protocol.CLUSTER_SLOTS);
  }

  public void geoadd(String key, double longitude, double latitude, String member) {
    geoadd(SafeEncoder.encode(key), longitude, latitude, SafeEncoder.encode(member));
  }

  public void geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
    geoadd(SafeEncoder.encode(key), convertMemberCoordinateMapToBinary(memberCoordinateMap));
  }

  public void geodist(String key, String member1, String member2) {
    geodist(SafeEncoder.encode(key), SafeEncoder.encode(member1), SafeEncoder.encode(member2));
  }

  public void geodist(String key, String member1, String member2, GeoUnit unit) {
    geodist(SafeEncoder.encode(key), SafeEncoder.encode(member1), SafeEncoder.encode(member2),
        unit);
  }

  public void geohash(String key, String... members) {
    geohash(SafeEncoder.encode(key), SafeEncoder.encodeMany(members));
  }

  public void geopos(String key, String[] members) {
    geopos(SafeEncoder.encode(key), SafeEncoder.encodeMany(members));
  }

  public void georadius(String key, double longitude, double latitude, double radius,
      GeoUnit unit) {
    georadius(SafeEncoder.encode(key), longitude, latitude, radius, unit);
  }

  public void georadius(String key, double longitude, double latitude, double radius, GeoUnit unit,
      GeoRadiusParam param) {
    georadius(SafeEncoder.encode(key), longitude, latitude, radius, unit, param);
  }

  public void georadiusByMember(String key, String member, double radius, GeoUnit unit) {
    georadiusByMember(SafeEncoder.encode(key), SafeEncoder.encode(member), radius, unit);
  }

  public void georadiusByMember(String key, String member, double radius, GeoUnit unit,
      GeoRadiusParam param) {
    georadiusByMember(SafeEncoder.encode(key), SafeEncoder.encode(member), radius, unit, param);
  }

  public void jset(final String key, final Path path, final String value) {
    jset(SafeEncoder.encode(key), SafeEncoder.encodeObject(path), SafeEncoder.encode(value));
  }

  public void jget(final String key, final Path path) {
    jget(SafeEncoder.encode(key), SafeEncoder.encodeObject(path));
  }

  public void jexists(final String... key) {
    jexists(SafeEncoder.encodeMany(key));
  }

  public void jdel(final String key, final Path path) {
    jdel(SafeEncoder.encode(key), SafeEncoder.encodeObject(path));
  }

  public void jmkget(final String... key) {
    jmkget(SafeEncoder.encodeMany(key));
  }

  public void jtype(final String key, final Path path) {
    jtype(SafeEncoder.encode(key), SafeEncoder.encodeObject(path));
  }

  public void jobjlen(final String key) {
    jobjlen(SafeEncoder.encode(key));
  }

  public void jarrappend(final String key, final Path path, final String value) {
    jarrappend(SafeEncoder.encode(key), SafeEncoder.encodeObject(path), SafeEncoder.encode(value));
  }

  public void jarrlen(final String key) {
    jarrlen(SafeEncoder.encode(key));
  }

  public void jarrpop(final String key) {
    jarrpop(SafeEncoder.encode(key));
  }

  private byte[][] getByteParams(String... params) {
    byte[][] p = new byte[params.length][];
    for (int i = 0; i < params.length; i++) {
      p[i] = SafeEncoder.encode(params[i]);
    }

    return p;
  }

  private HashMap<byte[], Double> convertScoreMembersToBinary(Map<String, Double> scoreMembers) {
    HashMap<byte[], Double> binaryScoreMembers = new HashMap<byte[], Double>();

    for (Entry<String, Double> entry : scoreMembers.entrySet()) {
      binaryScoreMembers.put(SafeEncoder.encode(entry.getKey()), entry.getValue());
    }
    return binaryScoreMembers;
  }

  private HashMap<byte[], GeoCoordinate> convertMemberCoordinateMapToBinary(
      Map<String, GeoCoordinate> memberCoordinateMap) {
    HashMap<byte[], GeoCoordinate> binaryMemberCoordinateMap = new HashMap<byte[], GeoCoordinate>();

    for (Entry<String, GeoCoordinate> entry : memberCoordinateMap.entrySet()) {
      binaryMemberCoordinateMap.put(SafeEncoder.encode(entry.getKey()), entry.getValue());
    }
    return binaryMemberCoordinateMap;
  }

  @Override
  public void bitfield(final String key, final String... arguments) {
    byte[][] argumentArray = new byte[arguments.length][];
    int index = 0;
    for (String argument : arguments) {
      argumentArray[index++] = SafeEncoder.encode(argument);
    }
    bitfield(SafeEncoder.encode(key), argumentArray);
  }

}
