# Apahce 에 대해 알아보자

## Introduce
이 문서는 유닉스 시스템에서 Apahce를 컴파일하고 설치하는것만 다룬다.

##[1] 컴파일과 설치
Apache 1.3은 Quick Install을 위해 자체 스크립트 설치를 이용하지만 Apahce 2.0 은 다른 오픈소스 프로젝트와 비슷한 환경을 만들기 위해 libtool 과 autoconf 를 사용한다.

### a. 성미 급한 사람들을 위한 개요

``` bash
lynx http://httpd.apache.org/download.cgi
gzip -d httpd-2_1.NN.tar.gz
tar xvf httpd-2_1.NN.tar
./configure --prefix=PREFIX    # PREFIX는 default 경로에 apache가 설치되지만,
                              # PREFIX path = /usr/local/apache2
                              # PREFIX대신 절대경로를 입력해면 의도한 경로대로 설치가 될것이다.
make
make Install
vi PREFIX/conf/httpd.conf    #이 떄의 RPEFIX는 설치된 apache 폴더의 디렉토리를 의미한다.
PREFIX/bin/apachectl start  # 설치한 아파치를 실행한다.
```

### b. 요구사항
아파치를 컴파일 하기위해 다음과 같은 것들이 필요하다.
-디스크 공간이 50MB 이상인가?
-ANSI-C 컴파일러와 커파일 시스템 gcc2.7.2 이상이면 된다.
-PATH 환경변수에는 기본적으로 make와 같은 기본적인 컴파일 도구는 포함되어야 한다.
-(선택) Perl 5 Perl 로 쓰여진 apxs나 dbcommanag 와 같이 지원되는 스크립트를 위핼 Perl5 인터프리터를
찾지 못해도 문제없이 아파치 2.0을 컴파일하고 사용할 수 있다.

### c. 다운로드
UNIX 시스템에서는 소스코드를 다운받아서 컴파일 하는것을 권장한다. 설치 바이너리를 이용하는 경우에는 INSTALL.bindist 파일의 지시를 따라서 설치하면 될것이다. PGP 서명을 가지고 다운로드한 타볼(tarball)을 검사하여 확인한다.  (tarball은 콘솔용 압축 프로그램이다.)
<http://example.com/>
<address@example.com>

### d. 소스트리 구성하기
아파치 소스 트리를 구성하ㅇ는 일이다. 이를 위해 배포본의 최상위 디렉토리에 있는 configure 스크립트를 사용한다.
아파치 소스 트리의 CVS 버전을 다운로드한 개발자는 autoconf 와 libtool이 설치되고 다음 과정으로 넘어가기 전에 buildconf를 실행해야 한다. (정식 버전에는 이 일련의 과정이 필요없지만...)

  모두 기본 옵션을 사용하여 소스트리를 구성하면 간단히 ./configure를 입력하면 된다. 기본 옵션을 수정하려면
./configure 에 여러 변수와 명령행 옵션을 사용한다. ./configure에서 가장 중요한 옵션은 아파치가 작동하기위해 아파치를 구성하고 설치할 장소인 --prefix 이다. configure 옵션들을 사용하여 파일의 위치를 더 자세히 설정할 수도 있다.

### e. 모듈 지정
모듈을 포함하거나 빼거 아파치에 포함될 기능을 선택한다. Base 상태인 모듈은 기본적으로 아파치에 포함된다. 다른 상태의 모듈은 --enable-module 옵선을 사용하여 포함한다. 여기서 module은 모듈이름에서 mod_를 빼고 밑줄을 빼기기호로 변경하면 된다.

--enable-mobule=shared 옵션을 사용하면 모듈을 실행중에 포함하거나 뺄 수 있는 공유 객체(Shared object, DSO)로 컴파일 한다. 또, --disable-module 옵션을 사용하여 Base 모듈을 뺄 수 있다. **지정한 모듈이 없어도 configure가 경고하지 않고 그냥 무시할 수 있기 때문에 정확히 입력해야 한다.**

가끔 configure 스크립트에게 컴파일러, 라이브러리, 헤더파일 등의 위치를 알려줘야 할 경우가 있다. 이 정보는 환경변수나 configure의 명령행 옵션을 사용하여 전달한다. 자세한 내용은
[configure manpage](https://httpd.apache.org/docs/2.4/programs/configure.html)를 참고하라. 여러분이 선택할 수 있는 가능성을 보여주기위해 다음은 특정 컴파일러와 플래그를 사용하고 나중에 DSO로 읽어들일 두 모듈 mod_rewrite와 mod_speling을 추가하여 /sw/pkg/apache에 설치할 아파치를 컴파일하는 전형적인 예이다:
``` bash
$ CC="pgcc" CFLAGS="-O2" \
./configure --prefix=/sw/pkg/apache \
--enable-rewrite=shared \
--enable-speling=shared
```


##[2] Apache의 시작
UNIX 에서의 httpd 프로그램은 apache를 실행함에 있어 백그라운드에서 지속적으로 요청을 처리하는 데몬으로 실해오딘다. 이 문서는 httpd를 시작하는 방법을 설명한다.

### a. Apache의 실행 원리.
실행파일에서 [Listen](https://httpd.apache.org/docs/2.4/mod/mpm_common.html#listen)이 기본값인 80(혹은 1024 이하의 다른포트) 라면 이 특권 포트(1~1024 내의 포트)에 연결하기 위해 root 권한이 필요하다. 서버는 시작하여 로그파일을 여는 등의 몇몇 기초적인 작업을 마친 후 클라이언트의 요청을 기다리고 응답하는 자식(Child) 프로세스를 여러개 띄워준다. 주 httpd 프로세스는 계속 root 사용자로 실행되지만 자식 프로세스들은 더 권한이 작은 사용자로 실행된다. 이는 선택한 [다중처리 모듈](https://httpd.apache.org/docs/2.4/mpm.html)로 조정한다.

httpd를 실행하면 가장 먼저 설정파일 httpd.conf 를 찾아서 읽는다. 이 파일의 위치는 컴파일중에 지정하나, 실행시 다음과 같이 -f 명령행 옵션으로 지정할 수 도 있다.

``` bash
/usr/local/apache2/bin/apachectl -f
/usr/local/apache2/conf/httpd.conf
```

### b. 시작중 오류
아파치가 시작하는 과정중에 심각한 문제가 발생하면, 종료하기 전에 문제를 알리는 문구를 콘솔이나 [ErrorLog](https://httpd.apache.org/docs/2.4/mod/core.html#errorlog)에 쓴다. 가장 흔한 오류문 중 하나는 "Unable to bind to Port ..."이다. 이 메세지는 보통 다음 두 경우에 발생한다.

* root 사용자가 아니고서 1024내의 포트로 서비스를 실행하려는 경우
* 이미 아파치나 다른 웹 서버가 사용중인 포트를 사용하려는 경우

### c. 부팅할 때 시작하기
시스템이 재시작한 후에도 서버가 계속 실행되길 바란다면, 시스템 시작파일(보통 rc.local이나 rc.N 디렉토리에 있는 파일)에 apachectl을 추가해야 한다. 이 경우 아파치는 root로 시작된다. 이전에 서버의 보안이나 접근 제한(파일권한)이 올바로 설정되었는지 확인하라.

apachectl은 표준 SysV init 스크립트와 비슷하게 동작하도록 만들어졌다. 스크립트는 아규먼트로 start, restart, stop을 받으면 각각 적절한 시그널을 httpd에 보낸다. 그래서 보통은 apachectl을 적절한 init 디렉토리로 링크를 걸면된다. 그러나 사용하는 시스템의 정확한 요구사항을 확인해야 한다.

### d. 참고
- [httpd](https://httpd.apache.org/docs/2.4/programs/httpd.html)
- [apachectl](https://httpd.apache.org/docs/2.4/programs/apachectl.html)


##[3] 중단과 재 시작
아파치를 중단하고 재시작하려면 실행되고 있는 httpd 프로세스에 시그널을 보내야 한다. 시그널을 보내는 방법은 두 가지가
1. UNIX kill [TERM, JUP, USR1,-9]  옵션들을 사용하여 프로세스에 직접 시그널을 보내는 방법.  
2. apachectl -k 옵션을 사용하여 stop, restart, graceful 을 이용하는 방법

시스템에 많은 httpd 가 실행되지만, PidFile 에 pid가 리고된 부모외에 다른 프로세스에 시그널을 보내면 재시작이나 중단이 되지 않는다. 부모 이외에 다른 시그널을 보내도 개발자가 의도한 대로의 작동이 실행되지 않게된다.
``` bash
# httpd.pid 파일에는 부모 프로세스 id가 적혀져있다.
 kill -TERM `cat /usr/local/apache2/logs/httpd.pid`

```

httpd 프로세스에게 시그널을 보내는 다른 방법은 명령행 옵션 -k를 사용하는 것이다. 아래서 설명할 stop, restart, graceful은 httpd 실행파일의 아규먼트들이다. 그러나 이 아규먼트들로 httpd를 실행하는 apachectl 스크립트를 사용하길 권한다.
(apachectl은 httpd 위에서 작동되고 있는 녀석으로써 apachectl에 보낸 명령이 httpd로 전달이 되어 httpd프로세스를 안전하게 보호하는 장치로써 httpd를 wrapping 한 녀석이다.)

httpd에 시그널을 보낸후, 다음 명령어로 진행상황을 알 수 있다:
``` bash
 tail -f /usr/local/apache2/logs/error_log

```
### a. 당장 중단.

**시그널: TERM**
``` bash
apachectl -k stop

```

TERM이나 stop 시그널을 부모에게 보내면 즉시 모든 자식을 죽인다. 자식을 완전히 죽이는데는 몇 초가 걸릴 수 있다. 그런후 부모가 종료한다. 처리중인 요청은 중단되고, 더 이상 요청을 받지않는다.

### b. 점잖은 재시작
**시그널: USR1**
``` bash
apachectl -k graceful

```

USR1이나 graceful 시그널을 부모에게 보내면 부모 프로세스는 자식들에게 현재 요청을 처리한후 종료하라고 (혹은 현재 아무것도 처리하지 않다면 즉시 종료하라고) 조언한다. 부모는 설정파일을 다시읽고 로그파일도 다시 연다. 자식이 죽을때마다 부모는 죽은 자식대신 새로운 설정 세대에 기초한 자식을 실행하여 즉시 요청을 처리하게 한다.

>점잖은 재시작(graceful restart)으로 USR1을 사용할 수 없는 플래폼에서는 대신 (WINCH와 같은) 다른 시그널을 사용할 수 있다. apachectl graceful은 플래폼에 알맞은 시그널을 보낸다.

점잖은 재시작은 항상 MPM의 프로세스 조절 지시어 설정을 고려하여, 재시작동안 클라이언트를 서비스하는 프로세스나 쓰레드가 적당한 수를 유지하도록 설계되었다. 게다가 StartServers는, 일초 후 최소한 [StartServers](https://httpd.apache.org/docs/2.4/mod/mpm_common.html#startservers)만큼 새로운 자식이 안만들어지면 자식이 StartServers 개가 되도록 새로 만든다. 즉, 프로그램은 서버의 현재 부하에 알맞은 자식의 개수를 유지하며, StartServers 파라미터로 지정한 당신의 기대를 존중한다.

[mod_status](https://httpd.apache.org/docs/2.4/mod/mod_status.html) 사용자는 USR1을 받을때 서버 통계가 0이 되지 않음을 봤을 것이다. 서버는 새로운 요청을 (운영체제는 이들을 큐에 담아서 어떤 경우에도 잃어버리지 않는다) 처리하지 못하는 시간을 최소화하고 당신의 튜닝 파라미터를 존중하도록 만들어졌다. 이를 위해 세대간 모든 자식을 기록하는 scoreboard를 유지한다.

status 모듈은 또한 점잖은 재시작 전에 시작하여 아직도 요청을 처리하고 있는 자식을 G로 알려준다.

현재로는 USR1을 사용하는 로그순환 스크립트가 재시작전에 모든 자식이 로그작성을 마쳤는지 알 수 있는 방법이 없다. 우리는 USR1 시그널을 보내고 적당한 시간이 지난후 이전 로그를 다루도록 제안한다. 예를 들어 낮은 대역폭 사용자의 경우 접속 대부분이 마치는데 10분이 안걸린다면, 이전 로그를 다루기전에 15분 기다린다.

>설정파일에 오류가 있다면 재시작시 부모는 재시작하지 않고 오류를 내며 종료한다. 또, 점잖은 재시작의 경우 종료할때 자식이 실행되도록 놔둔다. (자식들은 자신의 마지막 요청을 처리하고 "점잖게 종료한다".) 이는 서버를 재시작할때 문제가 된다. 서버는 자신이 기다릴 포트에 연결하지 못한다. 재시작전에 -t 명령행 옵션([httpd](https://httpd.apache.org/docs/2.4/programs/httpd.html) 참고)으로 설정파일 문법을 검사할 수 있다. 그러나 이런 검사도 서버가 올바로 재시작할지를 보장하지 못한다. 설정파일의 문법이 아닌 의미를 검사하려면 root가 아닌 사용자로 httpd를 시작해볼 수 있다. root가 아니기때문에 (아니면 현재 그 포트를 사용하는 httpd가 실행되기때문에) 오류가 없다면 소켓과 로그파일을 열려고 시도하는 과정에서 실패할 것이다. 다른 이유때문에 실패한다면 아마도 설정파일에 오류가 있을 것이다. 점잖은 재시작을 하기전에 오류를 고쳐야한다.


### c. 당장 재시작
**시그널: USR1**
``` bash
apachectl -k restart

```

HUP이나 restart 시그널을 부모에게 보내면 TERM과 같이 모든 자식을 죽이지만 부모는 종료하지 않는다. 부모는 설정파일을 다시읽고 로그파일을 다시 연다. 그리고 새로운 자식들을 만들고 서비스를 계속한다.

[mod_status](https://httpd.apache.org/docs/2.4/mod/mod_status.html) 사용자는 HUP를 보내면 서버 통계가 0이 됨을 알 수 있다.

>설정파일에 오류가 있다면 재시작을 해도 부모는 재시작하지 않고 오류를 내며 종료할 것이다. 이를 피하는 방법은 위를 참고하라.

### 부록 : 시그널과 레이스 컨디션
Apache 1.2b9 이전에는 재시작과 종료 시그널에 관계된 레이스 컨디션(race condition)이 있었다. (레이스 컨디션은 간단한 설명하자면, 어떤 일이 잘못된때 일어나서 기대한대로 동작하지 않는 시간에 민감한 문제다.) "올바른" 기능이 있는 아키텍쳐에서 우리는 이런 문제를 최대한 해결했다. 그러나 어떤 아키텍쳐에는 아직도 레이스 컨디션이 존재함을 주의하라.

> **ScoreBoard** :
> **Apache HTTP Server uses a scoreboard to communicate between its parent and child processes.** Some architectures require a file to facilitate this communication. If the file is left unspecified, Apache httpd first attempts to create the scoreboard entirely in memory (using anonymous shared memory) and, failing that, will attempt to create the file on disk (using file-based shared memory). Specifying this directive causes Apache httpd to always create the file on the disk.

> **
>

[ScoreBoardFile](https://httpd.apache.org/docs/2.4/mod/mpm_common.html#scoreboardfile)을 디스크에 저장하는 아키텍쳐는 scoreboard를 망가트릴 가능성이 있다. 그러면 (HUP후) "bind: Address already in use" 혹은 (USR1 후) "long lost child came home!"이 발생할 수 있다. 전자는 심각한 오류이고, 후자는 단지 서버가 scoreboard slot을 잃게 만든다. 그래서 강제 재시작을 줄이고 점잖은 재시작을 사용하길 추천한다. 이 문제는 해결하기 매우 힘들다. 그러나 다행히도 대부분의 아키텍쳐는 scoreboard로 파일을 사용하지 않는다. 파일을 사용하는 아키텍쳐라면 ScoreBoardFile 문서를 참고하라.

모든 아키텍쳐에는 지속되는 HTTP 연결 (KeepAlive)에서 두번째 이후 요청을 처리하는 자식에 약간의 레이스 컨디션이 있다. 자식은 요청줄을 읽은 후 요청 헤더를 읽기전에 종료할 수 있다. 이 문제는 너무 늦게 발견하여 1.2 버전이 나온후에야 수정되었다. 그러나 네트웍 지연이나 서버 시간제한때문에 KeepAlive 클라이언트는 이런 경우를 예상해야하기 때문에 이론상 문제는 안된다. 실제로 서버를 검사하기위해 일초에 20번 재시작하는 동안 클라이언트가 깨진 그림이나 빈 문서없이 사이트를 성공적으로 읽어들이길 기대하지 않는다면 문제가 안된다.


## [4] 설정 지시어 [httpd.conf Directions]
설정하는 지시어들에 관련된 리스트, 각 항목을 들어가야 무슨역할 하는지 알 수 있다.
**[설정 지시어 목록](https://httpd.apache.org/docs/2.4/mod/directives.html)**

**[설정 지시어 빠른 참조](https://httpd.apache.org/docs/2.4/mod/quickreference.html)**

## [5] 모듈 목록
모듈들에 대한 리스트, 각 항목당 어떤 역할을 하는지 알 수 있다.

### a. 핵심 기능과 다중 처리 모듈
1. **core** : Core Apache HTTP Server features that are always available
2. **mpm_common** : A collection of directives that are implemented by more than one multi-processing module (MPM)
3. **event** : A variant of the worker MPM with the goal of consuming threads only for connections with active  processing
4. **mpm_netware** : Multi-Processing Module implementing an exclusively threaded web server optimized for Novell NetWare
5. **mpmt_os2** : Hybrid multi-process, multi-threaded MPM for OS/2
6. **prefork** : Implements a non-threaded, pre-forking web server
7. **mpm_winnt** : Multi-Processing Module optimized for Windows NT.
8. **worker** : Multi-Processing Module implementing a hybrid multi-threaded multi-process web server

### b. 다른 모듈
**그 이외 다른 [mod_*.so](https://httpd.apache.org/docs/2.4/mod/#other)**
