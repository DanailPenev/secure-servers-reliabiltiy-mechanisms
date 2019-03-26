math.randomseed(os.time())

headers = {}
good_headers = "Basic VXNlcjpPYmZ1c2NhdGVkUGFzc3dvcmQ=" 
bad_headers = "Basic VXNlcjpGYWtlUGFzcw=="

-- 89/11 request split between good and bad requests
request = function()
  random_number = math.random(0, 100)
  headers["Authorization"] = good_headers
  if random_number <= 11 then
    headers["Authorization"] = bad_headers
  end

  return wrk.format(wrk.method, wrk.path, headers, wrk.body)
end

done = function(summary, latency, requests)
  f = io.open("result_authentication.csv", "a+")

  f:write(string.format("%f,%f,%f,%f,%f,%f,%f,%f,%d,%d,%d,%d\n",
  latency.min, latency.max, latency.mean, latency.stdev, latency:percentile(25),
  latency:percentile(50), latency:percentile(75), latency:percentile(99),
  summary["duration"], summary["requests"], summary["bytes"], summary["errors"]["status"]))
  
  f:close()
end
