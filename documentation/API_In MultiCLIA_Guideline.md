
# How to use ChatGPT in MultiCLIA

MultiCLIA allows you to use the OpenAI API, but for security reasons, you must generate your own API key. Follow these steps to get started.

## Getting Started

### 1. Create an OpenAI Account
- Go to [OpenAI's Platform](https://platform.openai.com/).
- Sign in or create a new account.

### 2. Generate an API Key
- Navigate to **API Keys** in your account settings.
- Click **"Create new secret key"**.
- Copy the key immediately, as you won’t be able to see it again.

### 3. Add Funds to Your OpenAI Account
- OpenAI services require payment for token usage.
- Go to the **Billing** section and add funds to your account.

### 4. Store the API Key in MultiCLIA
Once you have the API key, store it in the `.env` file inside the cloned MultiCLIA repository:

```sh
cd MultiCLIA
nano .env
```

Then, add the following line:

```env
OPENAI_API_KEY=your-api-key-here
```

Save and exit (`CTRL + X`, then `Y`, then `Enter`).

### 5. Run MultiCLIA
Test if everything is set up correctly, run MultiCLIA (you can find guidelines on how to start MultiCLIA in the README file on GitHub) and enter these commands:

```sh
/a
```

```sh
/i
```
And then you will see 

```
Model: gpt-3.5-turbo
Max Tokens: 200
Temperature: 0.7
```

If the setup is correct, MultiCLIA will be able to use ChatGPT successfully!
