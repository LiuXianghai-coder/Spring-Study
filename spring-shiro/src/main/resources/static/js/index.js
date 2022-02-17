const LOGIN_FORM = {
    data() {
        return {
            msg: {
                message: "hello world",
                votes: [
                    "                 __       \n" +
                    " _   __  ____   / /_  ___ \n" +
                    "| | / / / __ \\ / __/ / _ \\\n" +
                    "| |/ / / /_/ // /_  /  __/\n" +
                    "|___/  \\____/ \\__/  \\___/ "
                ]
            },
            login: {
                username: "",
                userAccount: "",
                password: "",
                submitButton: "登录"
            },
            shake: false,
            good: "",
            fake: {
                login: "vincent",
                password: "admin"
            }
        }
    },
    computed: {
        isShake: function () {
            console.log(this.shake);
            if (this.shake === true) {
                return 'shake'
            }
            return 'none'
        }
    },

    methods: {
        onSubmit: function (event) {
            event.preventDefault();
            this.shake = false;
            setTimeout(function () {
                if (
                    this.fake.login === this.login.login &&
                    this.fake.password === this.login.password
                ) {
                    console.log("登录成功")
                } else {
                    this.shake = true;
                }
            }, 3000)
            console.log(this.shake)

        }
    }
};