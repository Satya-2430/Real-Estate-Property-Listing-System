import styles from './Header.module.css';

function Header() {
    return(
    <header className={styles.Header}>
        <nav className={styles['main-nav-bar']}>
            <div className={styles.landingLogo}>
                <img className={styles.logoStyle} src="favicon-32x32.png"/>
            </div>
            <div>
                <a className={styles.getStartedBtn} target='_blank' href="#">Get Started</a>
            </div>
        </nav>
    </header>
    )
}

export default Header