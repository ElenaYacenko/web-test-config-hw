# Web Test Config

Пример конфигурации для веб-тестирования на Selenium с поддержкой локального и удалённого запуска.

## Конфигурация

Тесты используют два файла конфигурации:

| Файл | Браузер | Режим |
|---|---|---|
| `src/test/resources/local.properties` | Chrome | Локальный (ChromeDriver) |
| `src/test/resources/remote.properties` | Chrome | Удалённый (Selenoid/Selenium Grid) |

### Параметры

- `browser.name` — имя браузера (`CHROME`, `FIREFOX`)
- `browser.version` — версия браузера
- `run.type` — режим запуска (`local` или `remote`)
- `remote.url` — URL Selenium Grid / Selenoid (передаётся через `-Dremote.url=...`)

## Запуск

### Локальный запуск (Chrome)

```bash
./gradlew test -D"run.type=local"
```

### Удалённый запуск (Selenoid)

```bash
./gradlew test -D"run.type=remote" -D"remote.url=https://<login>:<password>@selenoid.autotests.cloud/wd/hub"
```

## Переключение режима

Для переключения между локальным и удалённым запуском используется одна системная переменная `run.type`. Значение браузера берётся из соответствующего файла `*.properties`.
