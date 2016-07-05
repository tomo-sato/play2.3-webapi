# play2.3-webapi
play2.3-webapi は、 Play framework 2.3 ベースで作成されたWEB APIアプリケーションベースです。<br>
アプリケーションビルドには、サブプロジェクト [play2.3-core](https://github.com/tomo-sato/play2.3-core) が必要です。<br>



## ビルド方法
アプリケーションをクローンします。
```
git clone https://github.com/tomo-sato/play2.3-webapi.git
```

アプリケーションフォルダ内に移動し、git サブモジュール（playサブプロジェクト [play2.3-core](https://github.com/tomo-sato/play2.3-core)）を初期化します。
```
cd play2.3-webapi
git submodule init
git submodule update
```

git サブモジュールのブランチをmasterに切り替えます。
```
cd modules\play2.3-core
git checkout master
```

以上でセットアップ完了です。



## 使用方法
play2.3-webapiフォルダに移動し、activatorコマンドで Plyaアプリケーションを起動します。
```
>activator run
[info] Loading project definition from C:\workspace\play2.3-webapi\project
[info] Set current project to play2.3-webapi (in build file:/C:/workspace/play2.3-webapi/)

--- (Running the application, auto-reloading is enabled) ---

[info] play - Listening for HTTP on /0:0:0:0:0:0:0:0:9000

(Server started, use Ctrl+D to stop and go back to the console...)
```

ブラウザより、下記URLにアクセスします。
* [http://localhost:9000/wapi/sample?name=太郎&age=36&birthday=19800408](http://localhost:9000/wapi/sample?name=太郎&age=36&birthday=19800408)

サーバで下記ログが出力され、レスポンスのjsonがブラウザに返却されれば成功です。
```
[info] Compiling 3 Java sources to C:\workspace\play2.3-webapi\target\scala-2.11\classes...
[info] play - Application started (Dev)
[info] a.e.s.Slf4jLogger - Slf4jLogger started
[info] j.d.w.a.UtilityAction - ■■■start■■■[lta4sl2PtC4ZltZYBc8p] method=GET, url=http://localhost:9000/wapi/sample?name=%E5%A4%AA%E9%83%8E&age=36&birthday=19800408：
[REQUEST PARAMS]
name:太郎
age:36
birthday:19800408

[info] o.h.v.i.u.Version - HV000001: Hibernate Validator 5.0.3.Final
[info] application - jp.dcworks.webapi.params.SampleParams{name=太郎, age=36, birthday=19800408}
[info] j.d.w.a.UtilityAction - ■■■ end ■■■[lta4sl2PtC4ZltZYBc8p] method=GET, url=http://localhost:9000/wapi/sample?name=%E5%A4%AA%E9%83%8E&age=36&birthday=19800408：
[RESPONSE PARAMS]
{
  "result": 0,
  "token": "hogehoge"
}
```

### レスポンス（正常）
```json
{
  "result": 0,
  "token": "hogehoge"
}
```

### レスポンス（異常）
```json
{
  "result": 1,
  "errors": {
    "birthday": [
      "誕生日（birthday）は必須です。"
    ],
    "name": [
      "名前（name）は必須です。"
    ],
    "age": [
      "年齢（age）は必須です。"
    ]
  }
}
```



## ドキュメント
* [play2.3-webapi アプリケーションベース API仕様](https://tomo-sato.github.io/play2.3-webapi/javadoc/)
    * [play2.3-core アプリケーションベース API仕様](https://tomo-sato.github.io/play2.3-core/javadoc/)



### ライセンス
Copyright 2016 Tomomichi Sato This software is licensed under the Apache 2 license, quoted below.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this project except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0.

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
