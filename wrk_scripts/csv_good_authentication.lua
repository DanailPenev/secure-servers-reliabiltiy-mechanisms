headers = {}
good_headers = "Basic VXNlcjpPYmZ1c2NhdGVkUGFzc3dvcmQ=" 

request = function()
  headers["Authorization"] = good_headers
  return wrk.format(wrk.method, wrk.path, headers, wrk.body)
end

done = function(summary, latency, requests)
  f = io.open("result_good_authentication.csv", "a+")
  
  f:write(string.format("%f,%f,%f,%f,%f,%f,%f,%f,%d,%d,%d,%d\n",
  latency.min, latency.max, latency.mean, latency.stdev, latency:percentile(25),
  latency:percentile(50), latency:percentile(75), latency:percentile(99),
  summary["duration"], summary["requests"], summary["bytes"], summary["errors"]["status"]))
  
  f:close()
end
