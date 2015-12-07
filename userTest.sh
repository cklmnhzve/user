#comments
Host="http://54.223.58.0:8888"
Token=""
AdminToken=""
user="testuser@asiainfo.com"

function getAdminToken() { 
    tokenURL="${Host}/permission/mob"
        admintoken=`curl  $tokenURL -H "Authorization: Basic ZGF0YWh1YkBhc2lhaW5mby5jb206NDZjNWZjODQ5MWI5NjMyNDAxYTIwN2M3YWIwNGViMGE=" -x proxy.asiainfo.com:8080`
    AdminToken=`echo $admintoken | cut -d \" -f 4`
    if [ ${#AdminToken} -ne 32 ];then
        echo "no admintoken avaliable"
    fi
    AdminToken="Token $AdminToken"
}

function chkResult() { 
    msg=`echo $1 | cut -d "," -f 2 | cut -d ":" -f 2`
    if [ "${msg:1:2}" != "OK" ];then
        echo "$1 xx"
    fi
}

getAdminToken
echo "AdminToken : $AdminToken"

echo '<---------------------------------------- 环境准备 	---------------------------------------->'

echo '<---------------------------------------- 清理数据 Start 	---------------------------------------->'

echo '<---------------------------------------- 清理数据 End 	---------------------------------------->'

echo 创建用户
result=`curl -i -X POST -H "Content-Type:application/json" ${Host}/users/$user?passwd=1111 -x proxy.asiainfo.com:8080`
echo $result

echo 激活用户
result=`curl -i -X PUT \
   -H "Content-Type:application/json" \
   -H "Authorization:$AdminToken" \
 ${Host}/users/$user/active -x proxy.asiainfo.com:8080`
 echo $result

echo 修改密码
result=`curl -i -X PUT \
   -H "Content-Type:application/json" \
   -H "Authorization:$AdminToken" \
   -d \
'{"passwd":"aaaaaa","oldpwd":"1111"}' \
 ${Host}/users/$user/pwd  -x proxy.asiainfo.com:8080`
 echo $result

echo 修改用户
result=`curl -i -X PUT \
   -H "Content-Type:application/json;charset=UTF-8" \
   -H "Authorization:$AdminToken" \
   -d \
'  {
        "usertype":"2",
        "userstatus":"3",
        "nickname":"foo",
        "username":"FOO",
        "comments":"测试用户",
        "passwd":"abcdef"
    }'\
 ${Host}/users/$user -x proxy.asiainfo.com:8080`
echo $result

echo 查询用户
result=`curl -i -X GET \
   -H "Content-Type:application/json" \
 ${Host}/users/$user -x proxy.asiainfo.com:8080`
echo $result

echo 删除用户
result=`curl -i -X DELETE \
   -H "Content-Type:application/json" \
   -H "Authorization:$AdminToken" \
 ${Host}/users/$user -x proxy.asiainfo.com:8080`
echo $result
