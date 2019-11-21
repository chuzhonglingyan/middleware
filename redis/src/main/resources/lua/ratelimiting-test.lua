-- redis-cli --eval ratelimiting-client.lua rate.limitingl:127.0.0.1 , 10 3

-- rate.limitingl + 1
local times = redis.call('incr',KEYS[1])

-- 第一次访问的时候加上过期时间10秒（10秒过后从新计数）
if times == 1 then
    redis.call('expire',KEYS[1], ARGV[1])
end

-- 注意，从redis进来的默认为字符串，lua同种数据类型只能和同种数据类型比较
if times > tonumber(ARGV[2]) then
    return 0
end
return 1