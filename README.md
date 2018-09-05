# MyDnsResolver
DNS过滤转发器 功能如下：
1. 能够接受客户端发送的域名并查找其ip，对于在黑名单中的域名返回给客户“not found”
2. 如果服务器未查找到域名，反馈给客户“please input the right domain name”
3. 若客户端的收发数据报时间超过三秒，弹出“请求超时”并退出。
