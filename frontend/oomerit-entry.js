import {LitElement, html, css} from 'lit-element';

export class OrderOfMeritEntry extends LitElement {

    static get styles() {
        return css`
        div.wrapper {
            height: 95px;
            border: 1px solid lightgray;
            margin-bottom: 5px;
        }
        
        div.container {
            position: relative;
            padding: 10px;
        }
    
        div.points {
            position: absolute;
            float: right;
            font-weight: bold;
            top: 20px;
            right: 20px;
        }
    
        h3 {
            font-size: 16px;
            font-weight: bold;
            margin-top: 0;
            margin-bottom: 0px;
        }
        
        .avatar {
            float: left;
            width: 75px;
            height: 75px;
            border-radius: 50%;
            overflow: hidden;
            background-position: center;
            background-size: 70%;
            background-repeat: no-repeat;
            margin: 10px;
        }
        `;
    }

    static get properties() {
        return {
            firstname: { type : String },
            lastname: { type : String },
            nickname: { type: String },
            points: { type: String }
        }
    }


    render() {
        return html`
            <div class="wrapper">
                <div class="avatar" style="background-image: url( /images/order.png);"></div>
                <div class="container">
                    <h3>${this.nickname}</h3>
                    <span>${this.firstname} ${this.lastname}</span>
                    <div class="points">${this.points}</div>
                </div>
            </div>`;
    }
}

customElements.define('oomerit-entry', OrderOfMeritEntry);
