-- keys
-- [delay-queue, redisson_delay_queue_timeout:{delay-queue}, redisson_delay_queue:{delay-queue}]
-- args
-- [System.currentTimeMillis(), 100]
local expiredValues = redis.call('zrangebyscore', KEYS[2], 0, ARGV[1], 'limit', 0, ARGV[2]);
if #expiredValues > 0 then
    for i, v in ipairs(expiredValues) do
        local randomId, value = struct.unpack('dLc0', v);
        redis.call('rpush', KEYS[1], value);
        redis.call('lrem', KEYS[3], 1, v);
    end ;
    redis.call('zrem', KEYS[2], unpack(expiredValues));
end ;
local v = redis.call('zrange', KEYS[2], 0, 0, 'WITHSCORES');
if v[1] ~= nil then
    return v[2];
end
return nil;