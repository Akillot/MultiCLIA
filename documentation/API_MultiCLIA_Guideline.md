# How to use APIs in MultiCLIA

MultiCLIA allows you to use different APIs for various features, but for security reasons,
you must generate your own API keys. Follow these steps to set up each API.

## Getting Started

---

## 1. ChatGPT API (OpenAI)

### Step 1: Create an OpenAI Account
- Visit [OpenAI's Platform](https://platform.openai.com/).
- Sign in or create a new account.

### Step 2: Generate an API Key
- Navigate to **API Keys** in your account settings.
- Click **"Create new secret key"**.
- Copy the key immediately, as you won’t be able to see it again.

### Step 3: Add Funds to Your OpenAI Account
- OpenAI services require payment for token usage.
- Go to the **Billing** section and add funds to your account.

### Step 4: Store the API Key in MultiCLIA
Once you have the API key, store it in the `.env` file inside the cloned MultiCLIA repository:

```sh
cd MultiCLIA
```

```sh
nano .env
```

Then, add the following line:

```sh
OPENAI_API_KEY=your-api-key-here
```

Save and exit (CTRL + X, then Y, then Enter).

### Step 5: Run MultiCLIA

```sh
/a
```

```sh
/i
```

You should see:

```sh
Model: gpt-3.5-turbo
Max Tokens: 200
Temperature: 0.7
```

If the setup is correct, MultiCLIA will be able to use ChatGPT successfully!

---

## 2. OpenWeather API (Weather Information)

### Step 1: Create an OpenWeather Account
- Visit [OpenWeather](https://openweathermap.org).
- Sign up or log in.

### Step 2: Generate an API Key
- Navigate to API keys in your account dashboard.
- Click "Generate" to create a new API key.
- Copy and store the key securely.

### Step 3: Store the API Key in MultiCLIA
Edit the .env file:

```sh
nano .env
```
Add this line:
```sh
OPEN_WEATHER_API_KEY=your-api-key-here
```
Save and exit (CTRL + X, then Y, then Enter).

### Step 4: Run MultiCLIA Weather Command
Start MultiCLIA and enter:
```sh
/w
```
Enter the city name when prompted. If configured correctly, you will see the weather details.

---

## 3. DeepL API (Translation)

### Step 1: Create a DeepL Account
- Visit [DeepL](https://www.deepl.com/en/your-account)
- Sign up or log in.

### Step 2: Get a Free API Key
- Navigate to the API section.
- Select the Free or Pro plan.
- Copy the API key.

### Step 3: Store the API Key in MultiCLIA
Edit the .env file:
```sh
nano .env
```
Add this line:
```sh
DEEPL_API_KEY=your-api-key-here
```
Save and exit (CTRL + X, then Y, then Enter).

### Step 4: Run MultiCLIA Translation Command
Start MultiCLIA and enter:
```sh
/tr
```

Enter the text to translate and the target language (e.g., EN for English, DE for German).
If everything is set up correctly, MultiCLIA will display the translation.

---

## Notes
- Never share your API keys publicly (e.g., on GitHub). Use a .gitignore file to exclude .env.
- If an API isn't working, double-check that your key is valid and that you've added it correctly to .env
