<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>hello, world!</title>
    <script src="vue/vue.js"></script>
</head>
<body>
<div id="app">{{ message }}</div>
<div id="app-2">
		<span v-bind:title="message"> Hover your mouse over me for a
			few seconds to see my dynamically bound title! </span>
</div>
<div id="app-3">
    <p v-if="seen">Now you see me</p>
</div>
<div id="app-4">
    <ol>
        <li v-for="todo in todos">{{ todo.text }}</li>
    </ol>
</div>
<div id="app-5">
    <p>{{ message }}</p>
    <button v-on:click="reverseMessage">Reverse Message</button>
</div>
<div id="app-6">
    <p>{{ message }}</p>
    <input v-model="message">
</div>
<script type="text/javascript">
    var app6 = new Vue({
        el: '#app-6',
        data: {
            message: 'Hello Vue!'
        }
    });

    var app5 = new Vue({
        el: '#app-5',
        data: {
            message: 'Hello Vue.js!'
        },
        methods: {
            reverseMessage: function () {
                this.message = this.message.split('').reverse().join('')
            }
        }
    });

    var app = new Vue({
        el: '#app',
        data: {
            message: 'Hello Vue!'
        }
    });

    var app2 = new Vue({
        el: '#app-2',
        data: {
            message: 'You loaded this page on ' + new Date()
        }
    });

    var app3 = new Vue({
        el: '#app-3',
        data: {
            seen: true
        }
    });

    var app4 = new Vue({
        el: '#app-4',
        created: function () {
            // `this` points to the vm instance
            console.log(this.todos);
        },
        data: {
            todos: [{
                text: 'Learn JavaScript'
            }, {
                text: 'Learn Vue'
            }, {
                text: 'Build something awesome'
            }]
        }
    });
</script>


<div id="app-7">

    <ol>
        <!--
        Now we provide each todo-item with the todo object
        it's representing, so that its content can be dynamic
        -->
        <todo-item v-for="todo in todos" v-bind:todo="todo"></todo-item>
    </ol>
</div>

<script type="text/javascript">
    Vue.component('todo-item', {
        props: ['todo'],
        template: '<li>{{ todo.text }}</li>'
    });
    var app7 = new Vue({
        el: '#app-7',
        data: {
            todos: [{
                text: 'Learn JavaScript'
            }, {
                text: 'Learn Vue'
            }, {
                text: 'Build something awesome'
            }]
        }
    });
</script>
</body>
</html>