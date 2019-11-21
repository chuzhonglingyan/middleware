-- redis-cli --eval ratelimiting-client.lua rate.limitingl:127.0.0.1 , 10 3

-- rate.limitingl + 1
local times = redis.call('incr',KEYS[1])


-- 获取ARGV[1],这里对应到应用端是一个List<Map>.
--  注意，这里接收到是的字符串，所以需要用csjon库解码成table类型
local receive_arg_json =  cjson.decode(ARGV[1])

--获取ARGV内的参数并打印
local expire = receive_arg_json.expire
local count = receive_arg_json.count


-- 第一次访问的时候加上过期时间10秒（10秒过后从新计数）
if times == 1 then
    redis.call('expire',KEYS[1], expire)
end

-- 注意，从redis进来的默认为字符串，lua同种数据类型只能和同种数据类型比较
if times > tonumber(count) then
    return false
end
return true