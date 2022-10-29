import { ToastProgrammatic as Toast } from 'buefy'

const Toasts =  {
    methods: {
        toast(message: string, type: string): void {
            Toast.open({
                duration: 5000,
                message: message,
                type: type,
                pauseOnHover: true,
                position: 'is-top',
            })
        }
    }
}

export { Toasts };