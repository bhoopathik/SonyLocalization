# SonyLocalization
 SonyLocalization

1. Used MVVM Architecture
2. Used Retrofit and OKHttp for Network calls.
3. The response format is JSON
4. This application makes 2 API calls
    a. Get all the languages supported
       API URL : https://demo6473270.mockable.io/localization
    b. Get the dictionary of that particular language.
       API URL : https://demo6473270.mockable.io/en.json
Pros:
5. With above approach, the we can support number of languages and corresponding dictionary map of a word/key
   SO that, new APK release is not required to support the new language.

Cons:
6. If we wanted to add/introduce the new text field in the layout, and that need language support, then new APK update/release is required.