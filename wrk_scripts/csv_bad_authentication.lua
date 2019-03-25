headers = {}
bad_headers = "Basic VXNlcjpGYWtlUGFzcw=="

request = function()
  headers["Authorization"] = bad_headers
  return wrk.format(wrk.method, wrk.path, headers, wrk.body)
end

done = function(summary, latency, requests)
  f = io.open("result_bad_authentication.csv", "a+")
  
  f:write(string.format("%f,%f,%f,%f,%f,%f,%f,%f,%d,%d,%d,%d\n",
  latency.min, latency.max, latency.mean, latency.stdev, latency:percentile(25),
  latency:percentile(50), latency:percentile(75), latency:percentile(99),
  summary["duration"], summary["requests"], summary["bytes"], summary["errors"]["status"]))
  
  f:close()
end
