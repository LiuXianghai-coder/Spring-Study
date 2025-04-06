-- keys
-- [chat_deque, redisson_delay_queue_timeout:{chat_deque}, redisson_delay_queue:{chat_deque}, redisson_delay_queue_channel:{chat_deque}]
-- args
-- [1743733478725, 1870427430521884606, PooledUnsafeDirectByteBuf(ridx: 0, widx: 12, cap: 256)]
local value = struct.pack('dLc0', tonumber(ARGV[2]), string.len(ARGV[3]), ARGV[3]);
redis.call('zadd', KEYS[2], ARGV[1], value);
redis.call('rpush', KEYS[3], value);
local v = redis.call('zrange', KEYS[2], 0, 0);
if v[1] == value then
    redis.call('publish', KEYS[4], ARGV[1]);
end ;