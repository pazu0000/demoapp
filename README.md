# demoapp - 投稿機能実装ガイド

## 📌 概要

このプロジェクトでは、Spring Boot + Thymeleaf + MyBatis を使用した「イシュー（課題）」管理アプリケーションの投稿機能を実装しています。

本READMEでは、新規投稿機能の実装手順とその構成についてまとめています。

---

## 🚀 技術スタック

- Java 21
- Spring Boot 3.2.7
- Gradle Groovy
- Thymeleaf
- MySQL
- Lombok
- MyBatis

---

## 🛠️ セットアップ手順

### 1. プロジェクトの作成（Spring Initializr）

以下の設定でプロジェクトを作成：

| 項目 | 内容 |
|------|------|
| Group | `demo` |
| Artifact / Name | `demoapp` |
| Package name | `in.techcamp.demoapp` |
| Packaging | Jar |
| Java | 21 |
| Dependencies | Spring Web, Thymeleaf, Lombok, MySQL Driver, MyBatis Framework |

> 📌 Spring Bootのバージョンは 3.2.7 を推奨。SNAPSHOT や M1 は避ける。

### 2. IntelliJ でプロジェクトを開く

- 解凍後、`build.gradle` を選択して「プロジェクトとして開く」

---

## 🗄️ データベース設定

### MySQL データベースの作成

```bash
mysql -u root
create database issue_app_java;
exit;
application.properties に接続設定を記述
properties

spring.sql.init.mode=always
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/issue_app_java
spring.datasource.username=root
spring.datasource.password=


テーブル作成スクリプト（schema.sql）

CREATE TABLE IF NOT EXISTS issues (
   id         INT       NOT NULL AUTO_INCREMENT,
   title      VARCHAR(256) NOT NULL,
   content    VARCHAR(256) NOT NULL,
   period     VARCHAR(256) NOT NULL,
   importance VARCHAR(256) NOT NULL,
   PRIMARY KEY (id)
);
配置場所：src/main/resources/schema.sql



📋 投稿機能構成
コントローラー
ファイル名：IssueController.java

@GetMapping("/issueForm")
public String showIssueForm(@ModelAttribute("issueForm") IssueForm form) {
    return "issueForm";
}

@PostMapping("/issues")
public String createIssue(IssueForm issueForm, Model model) {
    try {
        issueRepository.insert(
            issueForm.getTitle(),
            issueForm.getContent(),
            issueForm.getPeriod(),
            issueForm.getImportance()
        );
    } catch (Exception e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error";
    }
    return "redirect:/";
}


フォームクラス（Form）
IssueForm.java

public class IssueForm {
    private String title;
    private String content;
    private String period;
    private Character importance;
}


リポジトリ
IssueRepository.java

@Mapper
public interface IssueRepository {
    @Insert("INSERT INTO issues(title, content, period, importance) VALUES(#{title}, #{content}, #{period}, #{importance})")
    void insert(String title, String content, String period, Character importance);
}


ビュー（HTML）
issueForm.html
・投稿用フォーム（Thymeleaf使用）
・重要度はセレクトボックス（低・中・高）


💥 例外処理・エラーページ
・重要度未入力などで例外が発生した際に、error.html に遷移。
・Model にエラーメッセージを渡して表示。

🎨 CSS スタイリング
・CSSは src/main/resources/static/css/style.css に設置
・フォーム、ヘッダー、一覧などにデザインが適用済み

✅ 動作確認方法
①localhost:8080/issueForm にアクセス
②必要なフォームを入力
③「作成」を押す
④Sequel ProやMySQLで issues テーブルにレコードが追加されたことを確認
